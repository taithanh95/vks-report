package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.ReportPeriodInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReportPeriodInputRepository extends JpaRepository<ReportPeriodInput, Long> {
    ReportPeriodInput findFirstByRequestReportId(Long requestReportId);
}
