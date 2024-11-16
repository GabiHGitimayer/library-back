package com.biblioteca.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.HistoryEntity;
import com.biblioteca.entities.FineEntity;
import com.biblioteca.repositories.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historicoRepository;

    public void registrarHistorico(LoanEntity emprestimo, FineEntity multa) {
        HistoryEntity historico = new HistoryEntity();
        historico.setUserId(emprestimo.getUserId());
        historico.setBookId(emprestimo.getBookId());
        historico.setLoanId(emprestimo);

        if (emprestimo.getLoanDate() != null) {
            historico.setLoanDate(new Date(emprestimo.getLoanDate().getTime()));
        }
        
        if (emprestimo.getEfectiveReturnDate() != null) {
            historico.setReturnDate(new Date(emprestimo.getEfectiveReturnDate().getTime()));
        }
        
        historicoRepository.save(historico);
    }

    public void atualizarHistorico(LoanEntity emprestimo) {
        Optional<HistoryEntity> historicoOptional = historicoRepository.findByLoanId_loanId(emprestimo.getLoanId());

        HistoryEntity historico = historicoOptional.orElseGet(HistoryEntity::new);
        historico.setUserId(emprestimo.getUserId());
        historico.setBookId(emprestimo.getBookId());
        historico.setLoanId(emprestimo);
        

        if (emprestimo.getLoanDate() != null) {
            historico.setLoanDate(new Date(emprestimo.getLoanDate().getTime()));
        }
        
        if (emprestimo.getEfectiveReturnDate() != null) {
            historico.setReturnDate(new Date(emprestimo.getEfectiveReturnDate().getTime()));
        }

        historicoRepository.save(historico);
    }
    
    public List<HistoryEntity> listarHistorico(Long idUsuario) {
        return historicoRepository.findByUserId_userId(idUsuario);
    }
    
}