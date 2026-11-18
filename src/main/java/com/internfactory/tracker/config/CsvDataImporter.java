
package com.internfactory.tracker.config;

import com.internfactory.tracker.entity.Application;
import com.internfactory.tracker.repository.ApplicationRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class CsvDataImporter implements CommandLineRunner {

    private final ApplicationRepository repository;

    public CsvDataImporter(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource =
                new ClassPathResource("applications.csv");

        if (!resource.exists()) {
            System.out.println("CSV file not found!");
            return;
        }

        int imported = 0;
        int skipped = 0;

        try (Reader reader = new InputStreamReader(
                    resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext();

            if (headers == null) {
                System.out.println("CSV file is empty!");
                return;
            }

            Map<String, Integer> columns = new HashMap<>();

            for (int i = 0; i < headers.length; i++) {
                columns.put(headers[i].trim(), i);
            }

            String[] row;

            while ((row = csvReader.readNext()) != null) {

                if (row.length < headers.length) {
                    skipped++;
                    continue;
                }

                String id = get(row, columns, "application_id");

                if (id.isBlank() || repository.existsById(id)) {
                    skipped++;
                    continue;
                }

                Application app = new Application();

                app.setApplicationId(id);
                app.setSubmittedAt(get(row, columns, "submitted_at"));
                app.setFullName(get(row, columns, "full_name").trim());
                app.setEmail(get(row, columns, "email").trim());
                app.setMobile(get(row, columns, "mobile").trim());
                app.setCollege(get(row, columns, "college").trim());
                app.setDegreeProgram(get(row, columns, "degree_program"));
                app.setYear(get(row, columns, "year"));
                app.setPreferredDomain(
                        get(row, columns, "preferred_domain"));
                app.setTechnicalSkills(
                        get(row, columns, "technical_skills"));
                app.setResumeLink(get(row, columns, "resume_link"));
                app.setStatementOfInterest(
                        get(row, columns, "statement_of_interest"));

                app.setStatus("NEW");

                repository.save(app);
                imported++;
            }
        }

        System.out.println("--------------------------------");
        System.out.println("APPLICATION CSV IMPORT COMPLETED");
        System.out.println("Imported: " + imported);
        System.out.println("Skipped: " + skipped);
        System.out.println("--------------------------------");
    }

    private String get(String[] row,
                       Map<String, Integer> columns,
                       String name) {

        Integer index = columns.get(name);

        if (index == null || index >= row.length || row[index] == null) {
            return "";
        }

        return row[index];
    }
}
