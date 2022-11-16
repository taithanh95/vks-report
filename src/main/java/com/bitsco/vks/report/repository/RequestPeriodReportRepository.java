package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.RequestPeriodReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface RequestPeriodReportRepository extends JpaRepository<RequestPeriodReport, Long> {
    @Query(value = "SELECT a FROM RequestPeriodReport a WHERE 1 = 1 "
            + "AND(:reportCode IS NULL OR a.reportCode = :reportCode)"
            + "AND(:createdBy IS NULL OR a.createdBy = :createdBy)"
            + "AND(:beginAt IS NULL OR a.createdAt >= :beginAt) "
            + "AND(:endAt IS NULL OR a.createdAt < :endAt) "
            + "AND(:status IS NULL OR a.status = :status)"
            + "ORDER BY a.id DESC"
    )
    List<RequestPeriodReport> getList(
            @Param("reportCode") String reportCode,
            @Param("createdBy") String createdBy,
            @Param("beginAt") Date beginAt,
            @Param("endAt") Date endAt,
            @Param("status") Integer status
    );

    List<RequestPeriodReport> findByCreatedByAndStatusInOrderById(String createdBy, List<Integer> status);

    RequestPeriodReport findFirstByGenId(String genId);
}
