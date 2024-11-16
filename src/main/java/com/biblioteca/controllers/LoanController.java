package com.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.services.LoanService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService emprestimoService;
    
    @PostMapping("/do")
    public ResponseEntity<LoanEntity> realizarEmprestimo(@RequestBody LoanEntity emprestimo) {
        try {
            LoanEntity salvo = emprestimoService.realizarEmprestimo(emprestimo);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<LoanEntity> atualizarEmprestimo(@PathVariable Long id, @RequestBody LoanEntity emprestimoAtualizado) {
        try {
            LoanEntity emprestimo = emprestimoService.atualizarEmprestimo(id, emprestimoAtualizado);
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<LoanEntity>> listarEmprestimos() {
        try {
            List<LoanEntity> emprestimos = emprestimoService.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<LoanEntity> realizarDevolucao(@PathVariable Long id) {
        try {
            LoanEntity devolvido = emprestimoService.realizarDevolucao(id);
            return ResponseEntity.ok(devolvido);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/locate")
    public ResponseEntity<List<LoanEntity>> getAllEmprestimos() {
        try {
            List<LoanEntity> emprestimos = emprestimoService.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/locate/{id}")
    public ResponseEntity<LoanEntity> getEmprestimoById(@PathVariable Long id) {
        try {
            Optional<LoanEntity> emprestimo = emprestimoService.findById(id);
            if (emprestimo.isPresent()) {
                return ResponseEntity.ok(emprestimo.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        try {
            emprestimoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}