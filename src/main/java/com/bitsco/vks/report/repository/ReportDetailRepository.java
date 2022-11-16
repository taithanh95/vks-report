package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.ReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReportDetailRepository extends JpaRepository<ReportDetail, Long> {
    List<ReportDetail> findByRequestReportId(Long requestReportId);

    List<ReportDetail> findByRequestReportIdOrderByColumnNumAsc(Long requestReportId);
}
