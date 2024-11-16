package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.BookEntity;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.LoanRepository;
import com.biblioteca.repositories.BookRepository;
import com.biblioteca.repositories.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository emprestimoRepository;

    @Autowired
    private FineService multaService;
    
    @Autowired
    private UserRepository usuarioRepository;
    
    @Autowired
    private HistoryService historicoService;
    
    @Autowired
    private BookRepository livroRepository;

    public LoanEntity realizarEmprestimo(LoanEntity emprestimo) {
        UserEntity usuario = usuarioRepository.findById(emprestimo.getUserId().getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        BookEntity livro = livroRepository.findById(emprestimo.getBookId().getBookId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (livro.getCopiesQuantity() <= 0) {
            throw new RuntimeException("Não há exemplares disponíveis para empréstimo");
        }

        // Verifica se a data de empréstimo foi definida pelo usuário
        if (emprestimo.getLoanDate() == null) {
            throw new IllegalArgumentException("A data do empréstimo deve ser informada.");
        }

        // Calcula a data de devolução se ainda não foi definida
        if (emprestimo.getReturnDate() == null) {
            emprestimo.setReturnDate(emprestimo.calcularDataDevolucao(emprestimo.getLoanDate(), 7));
        }

        // Atualiza o estoque do livro
        livro.setCopiesQuantity(livro.getCopiesQuantity() - 1);
        livroRepository.save(livro);

        // Define o usuário e o livro no empréstimo e salva
        emprestimo.setUserId(usuario);
        emprestimo.setBookId(livro);
        LoanEntity salvo = emprestimoRepository.save(emprestimo);

        // Registra o histórico do empréstimo
        historicoService.registrarHistorico(salvo, null);

        return salvo;
    }
    
    public List<LoanEntity> findAll() {
        List<LoanEntity> emprestimos = emprestimoRepository.findAll();
        emprestimos.forEach(this::verificarStatusECalcularMulta);
        return emprestimos;
    }
    
    public LoanEntity atualizarEmprestimo(Long id, LoanEntity emprestimoAtualizado) {
        LoanEntity emprestimoExistente = emprestimoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        emprestimoExistente.setReturnDate(emprestimoAtualizado.getReturnDate());
        emprestimoExistente.setLoanStatus(emprestimoAtualizado.getLoanStatus());

        return emprestimoRepository.save(emprestimoExistente);
    }


    public Optional<LoanEntity> findById(Long id) {
        Optional<LoanEntity> emprestimo = emprestimoRepository.findById(id);
        emprestimo.ifPresent(this::verificarStatusECalcularMulta);
        return emprestimo;
    }
    
    public LoanEntity realizarDevolucao(Long idEmprestimo) {
        LoanEntity emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if ("Devolvido".equals(emprestimo.getLoanStatus())) {
            throw new RuntimeException("Este empréstimo já foi devolvido");
        }

        emprestimo.realizarDevolucao();
        
        BookEntity livro = emprestimo.getBookId();
        livro.setCopiesQuantity(livro.getCopiesQuantity() + 1);
        livroRepository.save(livro);

        if (emprestimo.getEfectiveReturnDate().after(emprestimo.getReturnDate())) {
            multaService.calcularMulta(emprestimo);
        }

        LoanEntity emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        historicoService.atualizarHistorico(emprestimoAtualizado);

        return emprestimoAtualizado;
    }

    public void deleteById(Long id) {
        emprestimoRepository.deleteById(id);
    }

    private void verificarStatusECalcularMulta(LoanEntity emprestimo) {
        emprestimo.verificarStatus();
        if ("Atrasado".equals(emprestimo.getLoanStatus())) {
            multaService.calcularMulta(emprestimo);
        }
    }
}
