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
    private FineService fineService;
    
    @Autowired
    private LoanService loanService;

    //TODO: adicionar uma rota para pegar a listagem das multas
    //TODO: adicionar uma rota para pegar a listagem de multas por usuário
    //TODO: alterar as rotas abaixo, não é necessário uma rota a mais para editar e salvar multas, pode ser apenas "/{id}"

    @PostMapping("/calculate/{loanId}")
    public ResponseEntity<FineEntity> calculateFine(@PathVariable Long loanId) {
        LoanEntity loan = loanService.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado!"));
        FineEntity fine = fineService.calculateFine(loan);
        return ResponseEntity.ok(fine);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<FineEntity> updateFine(@PathVariable Long id, @RequestBody FineEntity updatedFine) {
        try {
            FineEntity fine = fineService.updateFine(id, updatedFine);
            return ResponseEntity.ok(fine);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
