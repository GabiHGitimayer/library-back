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

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.MultaService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;
    
    @Autowired
    private EmprestimoService emprestimoService;

    /*@PostMapping("/calcular/{idEmprestimo}")
    public ResponseEntity<MultaEntity> calcularMulta(@PathVariable Long idEmprestimo) {
        EmprestimoEntity emprestimo = emprestimoService.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        MultaEntity multa = multaService.calcularMulta(emprestimo);
        return ResponseEntity.ok(multa);
    }*/
    
    @PostMapping("/calcular/{idEmprestimo}")
    public ResponseEntity<MultaEntity> calcularMulta(@PathVariable Long idEmprestimo) {
        EmprestimoEntity emprestimo = emprestimoService.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        MultaEntity multa = multaService.calcularMulta(emprestimo);
        return ResponseEntity.ok(multa);  // Retorna a entidade completa com o valor da multa
    }

    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MultaEntity> atualizarMulta(@PathVariable Long id, @RequestBody MultaEntity multaAtualizada) {
        try {
            MultaEntity multa = multaService.atualizarMulta(id, multaAtualizada);
            return ResponseEntity.ok(multa);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

}
