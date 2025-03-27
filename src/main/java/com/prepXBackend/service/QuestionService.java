package com.prepXBackend.service;

import com.prepXBackend.helper.CSVHelper;
import com.prepXBackend.model.Question;
import com.prepXBackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // ✅ Save Questions from CSV
    public void saveQuestionsFromCSV(MultipartFile file) {
        List<Question> questions = CSVHelper.csvToQuestions(file);
        questionRepository.saveAll(questions);
    }

    // ✅ Fetch Questions by Category & Difficulty
    public List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        return questionRepository.findByCategoryAndDifficulty(category, difficulty);
    }
    // ✅ Fetch ALL Questions (NEW METHOD)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
