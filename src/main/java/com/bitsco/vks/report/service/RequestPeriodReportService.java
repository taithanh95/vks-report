package com.bitsco.vks.report.service;

import com.bitsco.vks.report.entities.ReportPeriodDetail;
import com.bitsco.vks.report.entities.ReportPeriodInput;
import com.bitsco.vks.report.entities.RequestPeriodReport;
import com.bitsco.vks.report.request.RequestUpdate;
import com.bitsco.vks.report.response.RequestPeriodReportResponse;

import java.sql.SQLException;
import java.util.List;

public interface RequestPeriodReportService {

    RequestPeriodReport create(RequestPeriodReport requestReport) throws Exception;

    ReportPeriodInput updateReportPeriodInput(ReportPeriodInput reportInput) throws Exception;

    RequestPeriodReport delete(RequestPeriodReport requestReport) throws Exception;

    RequestPeriodReport findById(RequestPeriodReport requestReport) throws Exception;

    RequestPeriodReport detail(RequestPeriodReport requestReport) throws Exception;

    ReportPeriodInput getReportPeriodInput(RequestPeriodReport requestReport) throws Exception;

    ReportPeriodDetail updateValueManual(ReportPeriodDetail reportDetail) throws Exception;

    List<ReportPeriodDetail> findByRequestPeriodReportId(Long requestReportId) throws Exception;

    RequestPeriodReportResponse getDetail(Long requestReportId) throws Exception;

    List<RequestPeriodReport> getListRequestPeriodReportNewAndProcessing() throws Exception;

    List<RequestPeriodReport> getList(RequestPeriodReport requestReport) throws Exception;

    byte[] requestReportPdf(RequestPeriodReport request) throws Exception;

    byte[] requestReportPdfManual(RequestPeriodReport request) throws Exception;

    byte[] requestReportXlsx(RequestPeriodReport request) throws Exception;

    byte[] requestReportDocx(RequestPeriodReport request) throws Exception;

    RequestPeriodReport changeStatus(String genId, Integer status) throws Exception;

    List<ReportPeriodDetail> getList(String genId) throws SQLException;

    String updateValori(RequestUpdate requestUpdate) throws SQLException;
}
