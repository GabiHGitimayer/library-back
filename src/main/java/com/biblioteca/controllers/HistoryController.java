package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.HistoryEntity;
import com.biblioteca.services.HistoryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<HistoryEntity>> listHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(historyService.listHistory(userId));
    }
}

