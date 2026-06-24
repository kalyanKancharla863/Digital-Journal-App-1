package com.digital_journal_app.backend.service;

import com.digital_journal_app.backend.payload.JournalEntryRequestDTO;
import com.digital_journal_app.backend.payload.JournalEntryResponseDTO;

import java.util.List;

public interface JournalEntryService {
    JournalEntryResponseDTO createJournalEntry(JournalEntryRequestDTO journalEntryRequestDTO);
    List<JournalEntryResponseDTO> getJournalEntriesByUser();
    JournalEntryResponseDTO updateJournalEntry(Long id, JournalEntryRequestDTO journalEntryRequestDTO);
    JournalEntryResponseDTO deleteJournalEntry(Long id);
}