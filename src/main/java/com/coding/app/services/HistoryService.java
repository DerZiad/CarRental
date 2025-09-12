package com.coding.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coding.app.models.History;
import com.coding.app.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for managing history records of actions performed in the system.
 */
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    /**
     * Adds a new history record for the given action.
     *
     * @param action The action description.
     */
    public void addHistory(final String action) {
        historyRepository.save(new History(action));
    }

    /**
     * Retrieves all history records.
     *
     * @return List of History objects.
     */
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    /**
     * Clears all history records.
     */
    public void clearHistory() {
        historyRepository.deleteAll();
    }
}
