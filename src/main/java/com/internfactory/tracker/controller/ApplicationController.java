
package com.internfactory.tracker.controller;

import com.internfactory.tracker.entity.Application;
import com.internfactory.tracker.repository.ApplicationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationRepository repository;

    public ApplicationController(ApplicationRepository repository) {
        this.repository = repository;
    }

    // Get all applications with optional search and domain filter
    @GetMapping
    public List<Application> getApplications(
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String search) {

        return repository.searchApplications(domain, search);
    }

    // Get one application by ID
    @GetMapping("/{id}")
    public Application getApplication(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));
    }

    // Update application status
    @PutMapping("/{id}/status")
    public Application updateStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {

        Application application = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));

        String status = request.get("status");

        if (status == null ||
                !List.of("NEW", "SHORTLISTED", "REJECTED").contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid application status");
        }

        application.setStatus(status);

        return repository.save(application);
    }

    // Dashboard status counts
    @GetMapping("/counts")
    public Map<String, Long> getCounts() {

        return Map.of(
                "total", repository.count(),
                "new", repository.countByStatus("NEW"),
                "shortlisted", repository.countByStatus("SHORTLISTED"),
                "rejected", repository.countByStatus("REJECTED")
        );
    }
}
