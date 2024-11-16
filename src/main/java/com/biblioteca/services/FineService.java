package com.biblioteca.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.FineEntity;
import com.biblioteca.repositories.FineRepository;

@Service
public class FineService {

    @Autowired
    private FineRepository multaRepository;

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
    
    public FineEntity calcularMulta(LoanEntity emprestimo) {
        Date hoje = new Date();
        long diasAtraso = (hoje.getTime() - emprestimo.getReturnDate().getTime()) / (1000 * 60 * 60 * 24);
        
        FineEntity multa = new FineEntity();
        multa.setLoan(emprestimo);
        multa.setCalculationDate(hoje);

        if (diasAtraso > 0) {
            BigDecimal multaPorDia = new BigDecimal("5.00");
            multa.setFineValue(multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
        } else {
            multa.setFineValue(BigDecimal.ZERO);
        }

        return multaRepository.save(multa);
    }

    
    public FineEntity atualizarMulta(Long id, FineEntity multaAtualizada) {
        FineEntity multaExistente = multaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa não encontrada"));

        multaExistente.setFineValue(multaAtualizada.getFineValue());
        multaExistente.setCalculationDate(multaAtualizada.getCalculationDate());

        return multaRepository.save(multaExistente);
    }

}
