package com.prepXBackend.helper;

import com.prepXBackend.model.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    // ✅ Check if the uploaded file is a CSV
    public static boolean hasCSVFormat(MultipartFile file) {
        return file.getContentType().equals("text/csv");
    }

    // ✅ Convert CSV File to List of Questions
    public static List<Question> csvToQuestions(MultipartFile file) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                Question question = new Question(
                        csvRecord.get("category"),
                        csvRecord.get("difficulty"),
                        csvRecord.get("questionText"),
                        csvRecord.get("optionA"),
                        csvRecord.get("optionB"),
                        csvRecord.get("optionC"),
                        csvRecord.get("optionD"),
                        csvRecord.get("correctAnswer")
                );

                questions.add(question);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }

        return questions;
    }
}
