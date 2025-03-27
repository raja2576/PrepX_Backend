package com.prepXBackend.controller;

import com.prepXBackend.model.Question;
import com.prepXBackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // ✅ Fetch Questions by Category & Difficulty
//    @GetMapping("/{category}/{difficulty}")
//    public List<Question> getQuestions(@PathVariable String category, @PathVariable String difficulty) {
//        return questionService.getQuestionsByCategoryAndDifficulty(category, difficulty);
//    }
    
    // ✅ Use @RequestParam instead of @PathVariable
    @GetMapping
    public List<Question> getQuestions(
        @RequestParam String category, 
        @RequestParam String difficulty) {
        return questionService.getQuestionsByCategoryAndDifficulty(category, difficulty);
    }
    
    // ✅ Fetch ALL Questions (NEW API)
    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
