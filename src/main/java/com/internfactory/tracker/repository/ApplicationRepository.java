
package com.internfactory.tracker.repository;

import com.internfactory.tracker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, String> {

    @Query("""
        SELECT a FROM Application a
        WHERE (:domain IS NULL OR :domain = ''
               OR a.preferredDomain = :domain)
        AND (
            :search IS NULL OR :search = ''
            OR LOWER(a.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(a.college) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    List<Application> searchApplications(
            @Param("domain") String domain,
            @Param("search") String search
    );

    long countByStatus(String status);
}
