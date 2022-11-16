package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.ReportInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReportInputRepository extends JpaRepository<ReportInput, Long> {
    ReportInput findFirstByRequestReportId(Long requestReportId);
}
