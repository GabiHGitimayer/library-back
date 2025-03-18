package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.FineEntity;
import com.biblioteca.services.FineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fines")
public class FineController {

    @Autowired
    private FineService fineService;
    
    
    @GetMapping
    public ResponseEntity<List<FineEntity>> getFines() {
        return ResponseEntity.ok(fineService.listAllFines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FineEntity>> getMethodName(@RequestParam Long id) {
        return ResponseEntity.ok(fineService.listAllFinesById(id));
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
