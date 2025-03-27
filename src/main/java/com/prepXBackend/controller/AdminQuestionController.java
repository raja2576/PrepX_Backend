package com.prepXBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.prepXBackend.helper.CSVHelper;
import com.prepXBackend.model.Question;
import com.prepXBackend.repository.QuestionRepository;
import com.prepXBackend.service.QuestionService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access if needed
@RestController
@RequestMapping("/api/admin")
public class AdminQuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private QuestionService questionService;
    
 // ✅ Upload CSV File (Only for Admin)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: No file uploaded.");
        }

        if (!CSVHelper.hasCSVFormat(file)) {
            return ResponseEntity.badRequest().body("Error: Invalid file format. Please upload a CSV file.");
        }

        questionService.saveQuestionsFromCSV(file);
        return ResponseEntity.ok("CSV file uploaded and questions saved successfully!");
    }


    // ✅ Add Multiple Questions at Once
    @PostMapping("/add-questions")
    public ResponseEntity<String> addQuestions(@RequestBody List<Question> questions) {
        questionRepository.saveAll(questions);
        return ResponseEntity.ok("Questions added successfully!");
    }


}
