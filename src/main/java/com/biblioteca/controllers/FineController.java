package com.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.FineEntity;
import com.biblioteca.services.LoanService;
import com.biblioteca.services.FineService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fines")
public class FineController {

    @Autowired
    private FineService multaService;
    
    @Autowired
    private LoanService emprestimoService;

    @PostMapping("/calculate/{idEmprestimo}")
    public ResponseEntity<FineEntity> calcularMulta(@PathVariable Long idEmprestimo) {
        LoanEntity emprestimo = emprestimoService.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        FineEntity multa = multaService.calcularMulta(emprestimo);
        return ResponseEntity.ok(multa);  // Retorna a entidade completa com o valor da multa
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<FineEntity> atualizarMulta(@PathVariable Long id, @RequestBody FineEntity multaAtualizada) {
        try {
            FineEntity multa = multaService.atualizarMulta(id, multaAtualizada);
            return ResponseEntity.ok(multa);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

}
