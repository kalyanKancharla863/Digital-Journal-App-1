package com.digital_journal_app.backend.controller;

import com.digital_journal_app.backend.payload.JournalEntryRequestDTO;
import com.digital_journal_app.backend.payload.JournalEntryResponseDTO;
import com.digital_journal_app.backend.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        originPatterns = "*",
        allowCredentials = "true"
)
public class JounalEntryController {



    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/journal-entry")
    public ResponseEntity<JournalEntryResponseDTO> createJournalEntry(@RequestBody JournalEntryRequestDTO journalEntryRequestDTO) {
        JournalEntryResponseDTO createdJournalEntry = journalEntryService.createJournalEntry(journalEntryRequestDTO);
        return new ResponseEntity<>(createdJournalEntry, HttpStatus.CREATED);
    }

    @GetMapping("/journal-entries")
    public ResponseEntity<List<JournalEntryResponseDTO>> getJournalEntriesByUser() {
        List<JournalEntryResponseDTO> journalEntries = journalEntryService.getJournalEntriesByUser();
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }



    @PutMapping("/journal-entry/{id}")
    public ResponseEntity<JournalEntryResponseDTO> updateJournalEntry(@PathVariable Long id, @RequestBody JournalEntryRequestDTO journalEntryRequestDTO) {
        JournalEntryResponseDTO updatedJournalEntry = journalEntryService.updateJournalEntry(id, journalEntryRequestDTO);
        return new ResponseEntity<>(updatedJournalEntry, HttpStatus.OK);
    }   

    @DeleteMapping("/journal-entry/{id}")
    public ResponseEntity<JournalEntryResponseDTO> deleteJournalEntry(@PathVariable Long id) {
         JournalEntryResponseDTO deletedJournalEntry = journalEntryService.deleteJournalEntry(id);
        return new ResponseEntity<>(deletedJournalEntry, HttpStatus.OK);
    }

    
}