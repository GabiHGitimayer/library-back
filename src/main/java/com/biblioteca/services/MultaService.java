package com.biblioteca.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.repositories.MultaRepository;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    /*public MultaEntity calcularMulta(EmprestimoEntity emprestimo) {
        Date hoje = new Date();
        long diasAtraso = (hoje.getTime() - emprestimo.getDataDevolucaoEfetiva().getTime()) / (1000 * 60 * 60 * 24);
        if (diasAtraso > 0) {
            MultaEntity multa = new MultaEntity();
            multa.setEmprestimo(emprestimo);
            BigDecimal multaPorDia = new BigDecimal("5.00");
            multa.setValorMulta(multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
            multa.setDataCalculo(hoje);
            return multaRepository.save(multa);
        }
        return null;
    }*/
    
    public MultaEntity calcularMulta(EmprestimoEntity emprestimo) {
        Date hoje = new Date();
        long diasAtraso = (hoje.getTime() - emprestimo.getDataDevolucao().getTime()) / (1000 * 60 * 60 * 24);
        
        MultaEntity multa = new MultaEntity();
        multa.setEmprestimo(emprestimo);
        multa.setDataCalculo(hoje);

        if (diasAtraso > 0) {
            BigDecimal multaPorDia = new BigDecimal("5.00");
            multa.setValorMulta(multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
        } else {
            multa.setValorMulta(BigDecimal.ZERO);
        }

        return multaRepository.save(multa);
    }

    
    public MultaEntity atualizarMulta(Long id, MultaEntity multaAtualizada) {
        MultaEntity multaExistente = multaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa não encontrada"));

        multaExistente.setValorMulta(multaAtualizada.getValorMulta());
        multaExistente.setDataCalculo(multaAtualizada.getDataCalculo());

        return multaRepository.save(multaExistente);
    }

}
