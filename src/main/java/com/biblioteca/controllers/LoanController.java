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
    private LoanService loanService;
    
    @PostMapping("/do")
    public ResponseEntity<LoanEntity> doLoan(@RequestBody LoanEntity loan) {
        try {
            LoanEntity saved = loanService.doLoan(loan);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<LoanEntity> updateLoan(@PathVariable Long id, @RequestBody LoanEntity updatedLoan) {
        try {
            LoanEntity loan = loanService.updateLoan(id, updatedLoan);
            return ResponseEntity.ok(loan);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<LoanEntity>> listLoan() {
        try {
            List<LoanEntity> loans = loanService.findAll();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<LoanEntity> makeReturn(@PathVariable Long id) {
        try {
            LoanEntity returned = loanService.makeReturn(id);
            return ResponseEntity.ok(returned);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/locate")
    public ResponseEntity<List<LoanEntity>> listAllLoans() {
        try {
            List<LoanEntity> loans = loanService.findAll();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/locate/{id}")
    public ResponseEntity<LoanEntity> getLoanById(@PathVariable Long id) {
        try {
            Optional<LoanEntity> loan = loanService.findById(id);
            if (loan.isPresent()) {
                return ResponseEntity.ok(loan.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        try {
            loanService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}