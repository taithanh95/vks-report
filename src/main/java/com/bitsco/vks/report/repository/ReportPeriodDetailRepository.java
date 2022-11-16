package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.ReportPeriodDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReportPeriodDetailRepository extends JpaRepository<ReportPeriodDetail, Long> {
    List<ReportPeriodDetail> findByRequestReportId(Long requestReportId);

    List<ReportPeriodDetail> findByRequestReportIdOrderByColumnNumAsc(Long requestReportId);
}
