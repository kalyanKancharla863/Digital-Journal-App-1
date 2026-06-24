package com.digital_journal_app.backend.repositories;

import com.digital_journal_app.backend.model.JournalEntry;
import com.digital_journal_app.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long userId);

    List<JournalEntry> findByUser(User user);
}
