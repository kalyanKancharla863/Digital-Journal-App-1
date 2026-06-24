package com.digital_journal_app.backend.service;

import com.digital_journal_app.backend.exceptions.ResourceNotFoundException;
import com.digital_journal_app.backend.model.Category;
import com.digital_journal_app.backend.model.DifficultyLevel;
import com.digital_journal_app.backend.model.JournalEntry;
import com.digital_journal_app.backend.model.User;
import com.digital_journal_app.backend.payload.JournalEntryRequestDTO;
import com.digital_journal_app.backend.payload.JournalEntryResponseDTO;
import com.digital_journal_app.backend.repositories.CategoryRepository;
import com.digital_journal_app.backend.repositories.DifficultyLevelRepository;
import com.digital_journal_app.backend.repositories.JournalEntryRepository;
import com.digital_journal_app.backend.repositories.UserRepository;
import com.digital_journal_app.backend.util.AuthUtil;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalEntryServiceImpl implements  JournalEntryService {
    

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    @Autowired
    private ModelMapper modelMapper;

    

    @Override
    public JournalEntryResponseDTO createJournalEntry(JournalEntryRequestDTO journalEntryRequestDTO) {
        String username = authUtil.getLoggedInUser().getUserName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","username", username));

        Category category = categoryRepository.findById(journalEntryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", journalEntryRequestDTO.getCategoryId()));
       
        DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(journalEntryRequestDTO.getDifficultyLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("DifficultyLevel", "id", journalEntryRequestDTO.getDifficultyLevelId()));
       
                // Create a new JournalEntry entity
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(journalEntryRequestDTO.getTitle());
        journalEntry.setContent(journalEntryRequestDTO.getContent());
        journalEntry.setIsImportant(journalEntryRequestDTO.getIsImportant());
        journalEntry.setIsFavourite(journalEntryRequestDTO.getIsFavourite());
        journalEntry.setUser(user);
        journalEntry.setCategory(category);
        journalEntry.setDifficultyLevel(difficultyLevel);
        // Save the journal entry to the database
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);

        // Convert the saved entity to a response DTO
        return convertToDTO(savedJournalEntry);
    }

    @Override
    public List<JournalEntryResponseDTO> getJournalEntriesByUser() {
        String username = authUtil.getLoggedInUser().getUserName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","username", username));

        List<JournalEntry> journalEntries = journalEntryRepository.findByUser(user);
        return journalEntries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @NonNull
    private JournalEntryResponseDTO convertToDTO(JournalEntry journalEntry) {
        JournalEntryResponseDTO journalEntryResponseDTO = modelMapper.map(journalEntry, JournalEntryResponseDTO.class);
        journalEntryResponseDTO.setCategoryName(journalEntry.getCategory().getCategoryName());
        journalEntryResponseDTO.setDifficultyLevelName(journalEntry.getDifficultyLevel().getDifficultyLevelName());
        journalEntryResponseDTO.setCreatedAt(journalEntry.getCreatedTime());
        journalEntryResponseDTO.setUpdatedAt(journalEntry.getUpdateTime());
       journalEntryResponseDTO.setCategoryId(journalEntry.getCategory().getCategoryId());
       journalEntryResponseDTO.setDifficultyLevelId(journalEntry.getDifficultyLevel().getDifficultyLevelId());
        return journalEntryResponseDTO;
    }

    @Override
    public JournalEntryResponseDTO updateJournalEntry(Long id, JournalEntryRequestDTO journalEntryRequestDTO) {
        String username = authUtil.getLoggedInUser().getUserName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","username", username));

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JournalEntry", "id", id));

        if (!journalEntry.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("JournalEntry", "id", id);
        }

        Category category = categoryRepository.findById(journalEntryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", journalEntryRequestDTO.getCategoryId()));
       
        DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(journalEntryRequestDTO.getDifficultyLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("DifficultyLevel", "id", journalEntryRequestDTO.getDifficultyLevelId()));

        journalEntry.setTitle(journalEntryRequestDTO.getTitle());
        journalEntry.setContent(journalEntryRequestDTO.getContent());
        journalEntry.setIsImportant(journalEntryRequestDTO.getIsImportant());
        journalEntry.setIsFavourite(journalEntryRequestDTO.getIsFavourite());
        journalEntry.setCategory(category);
        journalEntry.setDifficultyLevel(difficultyLevel);

        JournalEntry updatedJournalEntry = journalEntryRepository.save(journalEntry);
        return convertToDTO(updatedJournalEntry);
    }

        @Override
    public JournalEntryResponseDTO deleteJournalEntry(Long id) {
        String username = authUtil.getLoggedInUser().getUserName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","username", username));

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JournalEntry", "id", id));

        if (!journalEntry.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("JournalEntry", "id", id);
        }

        journalEntryRepository.delete(journalEntry);
        return convertToDTO(journalEntry);
    }
}