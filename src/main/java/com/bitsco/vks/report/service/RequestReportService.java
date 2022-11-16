package com.bitsco.vks.report.service;

import com.bitsco.vks.report.entities.ReportDetail;
import com.bitsco.vks.report.entities.ReportInput;
import com.bitsco.vks.report.entities.RequestReport;
import com.bitsco.vks.report.model.Report;
import com.bitsco.vks.report.request.RequestUpdate;
import com.bitsco.vks.report.response.RequestReportResponse;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface RequestReportService {

    RequestReport create(RequestReport requestReport) throws Exception;

    ReportInput updateReportInput(ReportInput reportInput) throws Exception;

    RequestReport delete(RequestReport requestReport) throws Exception;

    RequestReport findById(RequestReport requestReport) throws Exception;

    RequestReport detail(RequestReport requestReport) throws Exception;

    ReportInput getReportInput(RequestReport requestReport) throws Exception;

    ReportDetail updateValueManual(ReportDetail reportDetail) throws Exception;

    List<ReportDetail> findByRequestReportId(Long requestReportId) throws Exception;

    RequestReportResponse getDetail(Long requestReportId) throws Exception;

    List<RequestReport> getListRequestReportNewAndProcessing() throws Exception;

    List<RequestReport> getList(RequestReport requestReport) throws Exception;

    byte[] requestReportPdf(RequestReport request) throws Exception;

    byte[] requestReportPdfManual(RequestReport request) throws Exception;

    byte[] requestReportXlsx(RequestReport request) throws Exception;

    byte[] requestReportDocx(RequestReport request) throws Exception;

    RequestReport changeStatus(String genId, Integer status) throws Exception;

    List<ReportDetail> getList(String genId) throws SQLException;

    String updateValori(RequestUpdate requestUpdate) throws SQLException;
}
