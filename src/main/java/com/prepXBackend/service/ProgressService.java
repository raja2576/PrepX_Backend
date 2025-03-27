package com.prepXBackend.service;

import com.prepXBackend.model.Progress;
import com.prepXBackend.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public void saveProgress(Progress progress) {
    	   try {
               progressRepository.save(progress);
               System.out.println("Progress saved to the database: " + progress); // Debugging line
           } catch (Exception e) {
               System.err.println("Error saving progress: " + e.getMessage());
           }
    }

    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserId(userId);
    }
}
