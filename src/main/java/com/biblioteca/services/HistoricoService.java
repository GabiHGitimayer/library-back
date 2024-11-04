package com.biblioteca.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.repositories.HistoricoRepository;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    public void registrarHistorico(EmprestimoEntity emprestimo, MultaEntity multa) {
        HistoricoEntity historico = new HistoricoEntity();
        historico.setUsuario(emprestimo.getUsuario());
        historico.setLivro(emprestimo.getLivro());
        historico.setEmprestimo(emprestimo);

        if (emprestimo.getDataEmprestimo() != null) {
            historico.setDataEmprestimo(new Date(emprestimo.getDataEmprestimo().getTime()));
        }
        
        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            historico.setDataDevolucao(new Date(emprestimo.getDataDevolucaoEfetiva().getTime()));
        }
        
        historicoRepository.save(historico);
    }

    public void atualizarHistorico(EmprestimoEntity emprestimo) {
        Optional<HistoricoEntity> historicoOptional = historicoRepository.findByEmprestimo_IdEmprestimo(emprestimo.getIdEmprestimo());

        HistoricoEntity historico = historicoOptional.orElseGet(HistoricoEntity::new);
        historico.setUsuario(emprestimo.getUsuario());
        historico.setLivro(emprestimo.getLivro());
        historico.setEmprestimo(emprestimo);
        

        if (emprestimo.getDataEmprestimo() != null) {
            historico.setDataEmprestimo(new Date(emprestimo.getDataEmprestimo().getTime()));
        }
        
        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            historico.setDataDevolucao(new Date(emprestimo.getDataDevolucaoEfetiva().getTime()));
        }

        historicoRepository.save(historico);
    }
    
    public List<HistoricoEntity> listarHistorico(Long idUsuario) {
        return historicoRepository.findByUsuario_IdUsuario(idUsuario);
    }
    
}