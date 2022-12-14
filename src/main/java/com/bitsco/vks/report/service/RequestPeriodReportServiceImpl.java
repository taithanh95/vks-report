package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.cache.CacheService;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.entities.ReportPeriodDetail;
import com.bitsco.vks.report.entities.ReportPeriodInput;
import com.bitsco.vks.report.entities.RequestPeriodReport;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.model.Report;
import com.bitsco.vks.report.model.User;
import com.bitsco.vks.report.repository.*;
import com.bitsco.vks.report.request.RequestUpdate;
import com.bitsco.vks.report.response.RequestPeriodReportResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class RequestPeriodReportServiceImpl implements RequestPeriodReportService {
    @Autowired
    RequestPeriodReportRepository requestReportRepository;

    @Autowired
    RequestReportDAO requestReportDAO;

    @Autowired
    ReportPeriodDetailRepository reportDetailRepository;

    @Autowired
    ReportPeriodInputRepository reportInputRepository;

    @Autowired
    LstReportRepository lstReportRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public RequestPeriodReport create(RequestPeriodReport requestReport) throws Exception {
        ValidateCommon.validateNullObject(requestReport.getReportCode(), "reportCode");
        requestReport.setCreatedBy(cacheService.getUsernameFromHeader());
        if (requestReport.getReportInput() == null)
            throw new CommonException(Response.MISSING_PARAM, "Thi???u ti??u ch?? b??o c??o");
        ReportPeriodInput reportInput = requestReport.getReportInput();
        ValidateCommon.validateNullObject(reportInput.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(reportInput.getToDate(), "toDate");
        ValidateCommon.validateNullObject(reportInput.getCreatedAt(), "createdAt");
        ValidateCommon.validateNullObject(reportInput.getSppId(), "sppId");
        requestReport.setStatus(ReportConstant.REQUEST_REPORT.STATUS.NEW);
        requestReport = requestReportRepository.save(requestReport);
        reportInput.setRequestReportId(requestReport.getId());
        reportInput.setSppname(reportInput.getSppname().toUpperCase());
        reportInputRepository.save(reportInput);
        return requestReport;
    }

    @Override
    public ReportPeriodInput updateReportPeriodInput(ReportPeriodInput reportInput) throws Exception {
        ValidateCommon.validateNullObject(reportInput.getId(), "id");
        ReportPeriodInput old = reportInputRepository.findById(reportInput.getId()).orElse(null);
        if (old == null || old.getRequestReportId() == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t??m th???y ti??u ch?? l???y b??o c??o");
        RequestPeriodReport requestReport = requestReportRepository.findById(old.getRequestReportId()).orElse(null);
        if (requestReport == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i y??u c???u b??o c??o");
        else if (requestReport.getStatus() == null)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng x??c ?????nh ???????c tr???ng th??i c???a y??u c???u b??o c??o");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.SUCCESS
                || requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.ERROR)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng th??? c???p nh???t khi y??u c???u l???y b??o c??o ???? ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.PROCESSING)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng th??? c???p nh???t khi y??u c???u l???y b??o c??o ??ang ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.DELETE)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ???? b??? x??a");
        reportInput = reportInputRepository.save(old.coppyFrom(reportInput));
        requestReport.setUpdatedBy(cacheService.getUsernameFromHeader());
        requestReportRepository.save(requestReport);
        return reportInput;
    }

    @Override
    public RequestPeriodReport delete(RequestPeriodReport requestReport) throws Exception {
        ValidateCommon.validateNullObject(requestReport.getId(), "id");
        RequestPeriodReport old = requestReportRepository.findById(requestReport.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        else if (old.getStatus() == null)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng x??c ?????nh ???????c tr???ng th??i y??u c???u b??o c??o");
        else if (old.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.DELETE)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u b??o c??o ???? b??? x??a tr?????c ????");
        else if (old.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.PROCESSING)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u b??o c??o ??ang x??? l?? n??n kh??ng th??? x??a");
        List<ReportPeriodDetail> reportDetailList = reportDetailRepository.findByRequestReportId(requestReport.getId());
        if (!ArrayListCommon.isNullOrEmpty(reportDetailList))
            reportDetailRepository.deleteAll(reportDetailList);
        old.setStatus(ReportConstant.REQUEST_REPORT.STATUS.DELETE);
        old.setUpdatedBy(cacheService.getUsernameFromHeader());
        requestReportRepository.save(old);
        return old;
    }

    @Override
    public RequestPeriodReport findById(RequestPeriodReport requestReport) throws Exception {
        ValidateCommon.validateNullObject(requestReport.getId(), "id");
        RequestPeriodReport response = requestReportRepository.findById(requestReport.getId()).orElse(null);
        if (response == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        return response;
    }

    @Override
    public RequestPeriodReport detail(RequestPeriodReport requestReport) throws Exception {
        ValidateCommon.validateNullObject(requestReport.getId(), "id");
        RequestPeriodReport response = requestReportRepository.findById(requestReport.getId()).orElse(null);
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReport.getId());
        if (response == null && reportInput == null) {
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        } else {
            assert response != null;
            response.setReportInput(reportInput);
            return response;
        }
    }

    @Override
    public ReportPeriodInput getReportPeriodInput(RequestPeriodReport requestReport) throws Exception {
        ValidateCommon.validateNullObject(requestReport.getId(), "id");
        ReportPeriodInput response = reportInputRepository.findFirstByRequestReportId(requestReport.getId());
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        return response;
    }

    @Override
    public List<RequestPeriodReport> getListRequestPeriodReportNewAndProcessing() throws Exception {
        List<RequestPeriodReport> list = requestReportRepository.findByCreatedByAndStatusInOrderById(
                cacheService.getUsernameFromHeader(),
                Arrays.asList(ReportConstant.REQUEST_REPORT.STATUS.NEW, ReportConstant.REQUEST_REPORT.STATUS.PROCESSING)
        );
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return list;
    }

    @Override
    public List<RequestPeriodReport> getList(RequestPeriodReport requestReport) {
        List<RequestPeriodReport> list = requestReportRepository.getList(
                StringCommon.isNullOrBlank(requestReport.getReportCode()) ? null : requestReport.getReportCode().trim(),
                StringCommon.isNullOrBlank(requestReport.getCreatedBy()) ? cacheService.getUsernameFromHeader() : requestReport.getCreatedBy().trim(),
                requestReport.getBeginAt(),
                requestReport.getEndAt() != null ? DateCommon.addDay(requestReport.getEndAt(), 1) : null,
                requestReport.getStatus());
        if (ArrayListCommon.isNullOrEmpty(list))
            throw new CommonException(Response.DATA_NOT_FOUND);
        return list;
    }

    @Override
    public byte[] requestReportPdf(RequestPeriodReport request) throws Exception {
        ValidateCommon.validateNullObject(request.getId(), "id");
        RequestPeriodReport requestReport = requestReportRepository.findById(request.getId()).orElse(null);
        if (requestReport == null || StringCommon.isNullOrBlank(requestReport.getReportCode()))
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_01))
            return requestReport01Pdf(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_02))
            return requestReport02Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_03))
            return requestReport03Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_04))
            return requestReport04Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_05))
            return requestReport05Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_06))
            return requestReport06Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_07))
            return requestReport07Pdf(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_08))
            return requestReport08Pdf(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_09))
            return requestReport09Pdf(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_10))
            return requestReport10Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_11))
            return requestReport11Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_12))
            return requestReport12Pdf(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_33))
            return requestReport33Pdf(requestReport.getId());
        else throw new CommonException(Response.DATA_INVALID, "M?? bi???u kh??ng t???n t???i");
    }

    @Override
    public byte[] requestReportPdfManual(RequestPeriodReport request) throws Exception {
        ValidateCommon.validateNullObject(request.getId(), "id");
        RequestPeriodReport requestReport = requestReportRepository.findById(request.getId()).orElse(null);
        if (requestReport == null || StringCommon.isNullOrBlank(requestReport.getReportCode()))
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_01))
            return requestReport01PdfManual(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_07))
            return requestReport07PdfManual(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_08))
            return requestReport08PdfManual(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_09))
            return requestReport09PdfManual(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_33))
            return requestReport33PdfManual(requestReport.getId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_02))
            return requestReport02PdfManual(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_03))
            return requestReport03PdfManual(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_05))
            return requestReport05PdfManual(requestReport);
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_06))
            return requestReport06PdfManual(requestReport);
        else throw new CommonException(Response.DATA_INVALID, "M?? bi???u kh??ng t???n t???i");
    }

    @Override
    public byte[] requestReportXlsx(RequestPeriodReport request) throws Exception {
        ValidateCommon.validateNullObject(request.getId(), "id");
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(request.getId());
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getBeginAt()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getEndAt()));
            File file = ResourceUtils.getFile("classpath:report/report-" + request.getReportCode() + ".jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsx(jp, "bao-cao-so-" + request.getReportCode());
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportXlsx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportXlsx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportDocx(RequestPeriodReport request) throws Exception {
        ValidateCommon.validateNullObject(request.getId(), "id");
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(request.getId());
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getBeginAt()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getEndAt()));
            File file = ResourceUtils.getFile("classpath:report/report-" + request.getReportCode() + ".jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportDocx(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportDocx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportDocx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    @Override
    public List<ReportPeriodDetail> findByRequestPeriodReportId(Long requestReportId) throws Exception {
        ValidateCommon.validateNullObject(requestReportId, "requestReportId");
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        else if (requestReport.getStatus() == null)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng x??c ?????nh ???????c tr???ng th??i c???a y??u c???u b??o c??o");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.NEW)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ch??a ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.PROCESSING)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ??ang ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.ERROR)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o x??? l?? kh??ng th??nh c??ng");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.DELETE)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ???? b??? x??a");
        List<ReportPeriodDetail> reportDetails = reportDetailRepository.findByRequestReportIdOrderByColumnNumAsc(requestReportId);
        if (ArrayListCommon.isNullOrEmpty(reportDetails)) throw new CommonException(Response.DATA_NOT_FOUND);
        if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_01)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_01;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_07)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_07;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_08)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_08;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_09)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_09;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_33)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_33;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else throw new CommonException(Response.DATA_INVALID, "M?? bi???u kh??ng t???n t???i");

        return reportDetails;
    }

    @Override
    public RequestPeriodReportResponse getDetail(Long requestReportId) throws Exception {
        ValidateCommon.validateNullObject(requestReportId, "requestReportId");
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        else if (requestReport.getStatus() == null)
            throw new CommonException(Response.DATA_INVALID, "Kh??ng x??c ?????nh ???????c tr???ng th??i c???a y??u c???u b??o c??o");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.NEW)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ch??a ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.PROCESSING)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ??ang ???????c x??? l??");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.ERROR)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o x??? l?? kh??ng th??nh c??ng");
        else if (requestReport.getStatus() == ReportConstant.REQUEST_REPORT.STATUS.DELETE)
            throw new CommonException(Response.DATA_INVALID, "Y??u c???u l???y b??o c??o ???? b??? x??a");
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        if (reportInput == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        String username = cacheService.getUsernameFromHeader();
        if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
        User user = cacheService.getUserFromCache(username);
        reportInput.setName(reportInput.getCreatedBy() == null ? user.getName() : reportInput.getCreatedBy());
        int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
        int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
        int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
        String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
        requestReport.setDate(date);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        switch (requestReport.getReportCode()) {
            case "02":
                reportDetails = getList(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "03":
                reportDetails = getList03(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "04":
                reportDetails = getList04(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "05":
                reportDetails = getList05(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "06":
                reportDetails = getList06(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "10":
                reportDetails = getList10(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "11":
                reportDetails = getList11(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            case "12":
                reportDetails = getList12(requestReport.getGenId());
                return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
            default:
                reportDetails = reportDetailRepository.findByRequestReportIdOrderByColumnNumAsc(requestReportId);
        }
//        List<ReportPeriodDetail> reportDetails = reportDetailRepository.findByRequestPeriodReportIdOrderByColumnNumAsc(requestReportId);
        if (ArrayListCommon.isNullOrEmpty(reportDetails)) throw new CommonException(Response.DATA_NOT_FOUND);
        if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_01)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_01;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_07)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_07;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_08)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_08;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_09)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_09;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_33)) {
            Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_33;
            reportDetails.forEach(x -> x.setDescriptionColumn(map.get(x.getColumnNum())));
        } else throw new CommonException(Response.DATA_INVALID, "M?? bi???u kh??ng t???n t???i");
        return new RequestPeriodReportResponse(reportDetails, requestReport, reportInput);
    }

    @Override
    public List<ReportPeriodDetail> getList(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.search(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setValue(StringCommon.isNullOrBlank(dt.getVal()) ? null : Integer.parseInt(dt.getVal()));
            detail.setValueManual(StringCommon.isNullOrBlank(dt.getValori()) ? null : Integer.parseInt(dt.getValori()));
            detail.setDescriptionColumn(dt.getRowname());
            detail.setColumnNum(Integer.parseInt(dt.getRowcode()));
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    @Override
    public String updateValori(RequestUpdate requestUpdate) throws SQLException {
        RequestPeriodReport requestReport = requestReportRepository.findById(requestUpdate.getRequestReportId()).orElse(null);
        if (requestReport == null)
            throw new CommonException(Response.DATA_NOT_FOUND);
        if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_02))
            return requestReportDAO.updateValori_02(requestUpdate, requestReport.getGenId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_03))
            return requestReportDAO.updateValori_03(requestUpdate, requestReport.getGenId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_05))
            return requestReportDAO.updateValori_05(requestUpdate, requestReport.getGenId());
        else if (requestReport.getReportCode().equals(ReportConstant.REQUEST_REPORT.REPORT_CODE.REPORT_06))
            return requestReportDAO.updateValori_06(requestUpdate, requestReport.getGenId());
        else throw new CommonException(Response.DATA_INVALID, "M?? bi???u kh??ng kh??ng ???????c c???p nh???t s??? li???u hi???u ch???nh");
    }

    private List<ReportPeriodDetail> getList03(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport03(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setValue(StringCommon.isNullOrBlank(dt.getVal()) ? null : Integer.parseInt(dt.getVal()));
            detail.setValueManual(StringCommon.isNullOrBlank(dt.getValori()) ? null : Integer.parseInt(dt.getValori()));
            detail.setDescriptionColumn(StringCommon.isNullOrBlank(dt.getRowname()) ? null : dt.getRowname());
            detail.setColumnNum(StringCommon.isNullOrBlank(dt.getRowcode()) ? null : Integer.parseInt(dt.getRowcode()));
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }


    private List<ReportPeriodDetail> getList04(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport04(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setLawname(StringCommon.isNullOrBlank(dt.getLawname()) ? null : dt.getLawname());
            detail.setLawid(StringCommon.isNullOrBlank(dt.getLawid()) ? null : dt.getLawid());
            detail.setCol3(dt.getCol3() == null ? null : dt.getCol3());
            detail.setCol4(dt.getCol4() == null ? null : dt.getCol4());
            detail.setCol5(dt.getCol5() == null ? null : dt.getCol5());
            detail.setCol6(dt.getCol6() == null ? null : dt.getCol6());
            detail.setCol7(dt.getCol7() == null ? null : dt.getCol7());
            detail.setCol8(dt.getCol8() == null ? null : dt.getCol8());
            detail.setCol9(dt.getCol9() == null ? null : dt.getCol9());
            detail.setCol10(dt.getCol10() == null ? null : dt.getCol10());
            detail.setCol11(dt.getCol11() == null ? null : dt.getCol11());
            detail.setCol12(dt.getCol12() == null ? null : dt.getCol12());
            detail.setCol13(dt.getCol13() == null ? null : dt.getCol13());
            detail.setCol14(dt.getCol14() == null ? null : dt.getCol14());
            detail.setCol15(dt.getCol15() == null ? null : dt.getCol15());
            detail.setCol16(dt.getCol16() == null ? null : dt.getCol16());
            detail.setCol17(dt.getCol17() == null ? null : dt.getCol17());
            detail.setCol18(dt.getCol18() == null ? null : dt.getCol18());
            detail.setCol19(dt.getCol19() == null ? null : dt.getCol19());
            detail.setCol20(dt.getCol20() == null ? null : dt.getCol20());
            detail.setCol21(dt.getCol21() == null ? null : dt.getCol21());
            detail.setCol22(dt.getCol22() == null ? null : dt.getCol22());
            detail.setCol23(dt.getCol23() == null ? null : dt.getCol23());
            detail.setCol24(dt.getCol24() == null ? null : dt.getCol24());
            detail.setCol25(dt.getCol25() == null ? null : dt.getCol25());
            detail.setCol26(dt.getCol26() == null ? null : dt.getCol26());
            detail.setCol27(dt.getCol27() == null ? null : dt.getCol27());
            detail.setCol28(dt.getCol28() == null ? null : dt.getCol28());
            detail.setCol29(dt.getCol29() == null ? null : dt.getCol29());
            detail.setCol30(dt.getCol30() == null ? null : dt.getCol30());
            detail.setCol31(dt.getCol31() == null ? null : dt.getCol31());
            detail.setCol32(dt.getCol32() == null ? null : dt.getCol32());
            detail.setCol33(dt.getCol33() == null ? null : dt.getCol33());
            detail.setCol34(dt.getCol34() == null ? null : dt.getCol34());
            detail.setCol35(dt.getCol35() == null ? null : dt.getCol35());
            detail.setCol36(dt.getCol36() == null ? null : dt.getCol36());
            detail.setCol37(dt.getCol37() == null ? null : dt.getCol37());

            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    private List<ReportPeriodDetail> getList05(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport05(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setValue(StringCommon.isNullOrBlank(dt.getVal()) ? null : Integer.parseInt(dt.getVal()));
            detail.setValueManual(StringCommon.isNullOrBlank(dt.getValori()) ? null : Integer.parseInt(dt.getValori()));
            detail.setDescriptionColumn(StringCommon.isNullOrBlank(dt.getRowname()) ? null : dt.getRowname());
            detail.setColumnNum(StringCommon.isNullOrBlank(dt.getRowcode()) ? null : Integer.parseInt(dt.getRowcode()));
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    private List<ReportPeriodDetail> getList06(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport06(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setValue(StringCommon.isNullOrBlank(dt.getVal()) ? null : Integer.parseInt(dt.getVal()));
            detail.setValueManual(StringCommon.isNullOrBlank(dt.getValori()) ? null : Integer.parseInt(dt.getValori()));
            detail.setDescriptionColumn(StringCommon.isNullOrBlank(dt.getRowname()) ? null : dt.getRowname());
            detail.setColumnNum(StringCommon.isNullOrBlank(dt.getRowcode()) ? null : Integer.parseInt(dt.getRowcode()));
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }


    private List<ReportPeriodDetail> getList10(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport10(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setLawname(StringCommon.isNullOrBlank(dt.getLawname()) ? null : dt.getLawname());
            detail.setLawid(StringCommon.isNullOrBlank(dt.getLawid()) ? null : dt.getLawid());
            detail.setCol3(dt.getCol3() == null ? null : dt.getCol3());
            detail.setCol4(dt.getCol4() == null ? null : dt.getCol4());
            detail.setCol5(dt.getCol5() == null ? null : dt.getCol5());
            detail.setCol6(dt.getCol6() == null ? null : dt.getCol6());
            detail.setCol7(dt.getCol7() == null ? null : dt.getCol7());
            detail.setCol8(dt.getCol8() == null ? null : dt.getCol8());
            detail.setCol9(dt.getCol9() == null ? null : dt.getCol9());
            detail.setCol10(dt.getCol10() == null ? null : dt.getCol10());
            detail.setCol11(dt.getCol11() == null ? null : dt.getCol11());
            detail.setCol12(dt.getCol12() == null ? null : dt.getCol12());
            detail.setCol13(dt.getCol13() == null ? null : dt.getCol13());
            detail.setCol14(dt.getCol14() == null ? null : dt.getCol14());
            detail.setCol15(dt.getCol15() == null ? null : dt.getCol15());
            detail.setCol16(dt.getCol16() == null ? null : dt.getCol16());
            detail.setCol17(dt.getCol17() == null ? null : dt.getCol17());
            detail.setCol18(dt.getCol18() == null ? null : dt.getCol18());
            detail.setCol19(dt.getCol19() == null ? null : dt.getCol19());
            detail.setCol20(dt.getCol20() == null ? null : dt.getCol20());
            detail.setCol21(dt.getCol21() == null ? null : dt.getCol21());
            detail.setCol22(dt.getCol22() == null ? null : dt.getCol22());
            detail.setCol23(dt.getCol23() == null ? null : dt.getCol23());
            detail.setCol24(dt.getCol24() == null ? null : dt.getCol24());
            detail.setCol25(dt.getCol25() == null ? null : dt.getCol25());
            detail.setCol26(dt.getCol26() == null ? null : dt.getCol26());
            detail.setCol27(dt.getCol27() == null ? null : dt.getCol27());
            detail.setCol28(dt.getCol28() == null ? null : dt.getCol28());
            detail.setCol29(dt.getCol29() == null ? null : dt.getCol29());
            detail.setCol30(dt.getCol30() == null ? null : dt.getCol30());
            detail.setCol31(dt.getCol31() == null ? null : dt.getCol31());
            detail.setCol32(dt.getCol32() == null ? null : dt.getCol32());
            detail.setCol33(dt.getCol33() == null ? null : dt.getCol33());
            detail.setCol34(dt.getCol34() == null ? null : dt.getCol34());
            detail.setCol35(dt.getCol35() == null ? null : dt.getCol35());
            detail.setCol36(dt.getCol36() == null ? null : dt.getCol36());
            detail.setCol37(dt.getCol37() == null ? null : dt.getCol37());
            detail.setCol38(dt.getCol38() == null ? null : dt.getCol38());
            detail.setCol39(dt.getCol39() == null ? null : dt.getCol39());
            detail.setCol40(dt.getCol40() == null ? null : dt.getCol40());
            detail.setCol41(dt.getCol41() == null ? null : dt.getCol41());
            detail.setCol42(dt.getCol42() == null ? null : dt.getCol42());
            detail.setCol43(dt.getCol43() == null ? null : dt.getCol43());
            detail.setCol44(dt.getCol44() == null ? null : dt.getCol44());
            detail.setCol45(dt.getCol45() == null ? null : dt.getCol45());
            detail.setCol46(dt.getCol46() == null ? null : dt.getCol46());
            detail.setCol47(dt.getCol47() == null ? null : dt.getCol47());
            detail.setCol48(dt.getCol48() == null ? null : dt.getCol48());
            detail.setCol49(dt.getCol49() == null ? null : dt.getCol49());
            detail.setCol50(dt.getCol50() == null ? null : dt.getCol50());
            detail.setCol51(dt.getCol51() == null ? null : dt.getCol51());
            detail.setCol52(dt.getCol52() == null ? null : dt.getCol52());
            detail.setCol53(dt.getCol53() == null ? null : dt.getCol53());
            detail.setCol54(dt.getCol54() == null ? null : dt.getCol54());
            detail.setCol55(dt.getCol55() == null ? null : dt.getCol55());
            detail.setCol56(dt.getCol56() == null ? null : dt.getCol56());
            detail.setCol57(dt.getCol57() == null ? null : dt.getCol57());
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    private List<ReportPeriodDetail> getList11(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport11(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setLawname(StringCommon.isNullOrBlank(dt.getLawname()) ? null : dt.getLawname());
            detail.setLawid(StringCommon.isNullOrBlank(dt.getLawid()) ? null : dt.getLawid());
            detail.setCol3(dt.getCol3() == null ? null : dt.getCol3());
            detail.setCol4(dt.getCol4() == null ? null : dt.getCol4());
            detail.setCol5(dt.getCol5() == null ? null : dt.getCol5());
            detail.setCol6(dt.getCol6() == null ? null : dt.getCol6());
            detail.setCol7(dt.getCol7() == null ? null : dt.getCol7());
            detail.setCol8(dt.getCol8() == null ? null : dt.getCol8());
            detail.setCol9(dt.getCol9() == null ? null : dt.getCol9());
            detail.setCol10(dt.getCol10() == null ? null : dt.getCol10());
            detail.setCol11(dt.getCol11() == null ? null : dt.getCol11());
            detail.setCol12(dt.getCol12() == null ? null : dt.getCol12());
            detail.setCol13(dt.getCol13() == null ? null : dt.getCol13());
            detail.setCol14(dt.getCol14() == null ? null : dt.getCol14());
            detail.setCol15(dt.getCol15() == null ? null : dt.getCol15());
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    private List<ReportPeriodDetail> getList12(String genId) throws SQLException {
        List<Report> lst = lstReportRepository.getListReport12(genId);
        List<ReportPeriodDetail> reportDetails1 = new ArrayList<>();
        lst.forEach(dt -> {
            ReportPeriodDetail detail = new ReportPeriodDetail();
            detail.setLawname(StringCommon.isNullOrBlank(dt.getLawname()) ? null : dt.getLawname());
            detail.setLawid(StringCommon.isNullOrBlank(dt.getLawid()) ? null : dt.getLawid());
            detail.setCol3(dt.getCol3() == null ? null : dt.getCol3());
            detail.setCol4(dt.getCol4() == null ? null : dt.getCol4());
            detail.setCol5(dt.getCol5() == null ? null : dt.getCol5());
            detail.setCol6(dt.getCol6() == null ? null : dt.getCol6());
            detail.setCol7(dt.getCol7() == null ? null : dt.getCol7());
            detail.setCol8(dt.getCol8() == null ? null : dt.getCol8());
            detail.setCol9(dt.getCol9() == null ? null : dt.getCol9());
            detail.setCol10(dt.getCol10() == null ? null : dt.getCol10());
            detail.setCol11(dt.getCol11() == null ? null : dt.getCol11());
            detail.setCol12(dt.getCol12() == null ? null : dt.getCol12());
            detail.setCol13(dt.getCol13() == null ? null : dt.getCol13());
            detail.setCol14(dt.getCol14() == null ? null : dt.getCol14());
            detail.setCol15(dt.getCol15() == null ? null : dt.getCol15());
            detail.setCol16(dt.getCol16() == null ? null : dt.getCol16());
            detail.setCol17(dt.getCol17() == null ? null : dt.getCol17());
            detail.setCol18(dt.getCol18() == null ? null : dt.getCol18());
            detail.setCol19(dt.getCol19() == null ? null : dt.getCol19());
            detail.setCol20(dt.getCol20() == null ? null : dt.getCol20());
            detail.setCol21(dt.getCol21() == null ? null : dt.getCol21());
            reportDetails1.add(detail);
        });
        return reportDetails1;
    }

    public byte[] requestReport01Pdf(long requestReportId) throws Exception {
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_01;
        reportDetails.forEach(x -> {
            x.setDescriptionColumn(map.get(x.getColumnNum()));
        });
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-01.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport01PdfManual(long requestReportId) throws Exception {
        List<ReportPeriodDetail> details = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        if (ArrayListCommon.isNullOrEmpty(details)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_01;
        details.forEach(x -> reportDetails.add(new ReportPeriodDetail(x.getColumnNum(), x.getValueManual() != null ? x.getValueManual() : x.getValue(), map.get(x.getColumnNum()))));
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-01.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport07Pdf(long requestReportId) throws Exception {
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_07;
        reportDetails.forEach(x -> {
            x.setDescriptionColumn(map.get(x.getColumnNum()));
        });
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-07.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport07PdfManual(long requestReportId) throws Exception {
        List<ReportPeriodDetail> details = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        if (ArrayListCommon.isNullOrEmpty(details)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_07;
        details.forEach(x -> reportDetails.add(new ReportPeriodDetail(x.getColumnNum(), x.getValueManual() != null ? x.getValueManual() : x.getValue(), map.get(x.getColumnNum()))));
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-07.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport08Pdf(long requestReportId) throws Exception {
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_08;
        reportDetails.forEach(x -> {
            x.setDescriptionColumn(map.get(x.getColumnNum()));
        });
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-08.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport08PdfManual(long requestReportId) throws Exception {
        List<ReportPeriodDetail> details = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        if (ArrayListCommon.isNullOrEmpty(details)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_08;
        details.forEach(x -> reportDetails.add(new ReportPeriodDetail(x.getColumnNum(), x.getValueManual() != null ? x.getValueManual() : x.getValue(), map.get(x.getColumnNum()))));
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-08.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport09Pdf(long requestReportId) throws Exception {
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_09;
        reportDetails.forEach(x -> {
            x.setDescriptionColumn(map.get(x.getColumnNum()));
        });
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-09.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport09PdfManual(long requestReportId) throws Exception {
        List<ReportPeriodDetail> details = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        if (ArrayListCommon.isNullOrEmpty(details)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_09;
        details.forEach(x -> reportDetails.add(new ReportPeriodDetail(x.getColumnNum(), x.getValueManual() != null ? x.getValueManual() : x.getValue(), map.get(x.getColumnNum()))));
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-09.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport33Pdf(long requestReportId) throws Exception {
        List<ReportPeriodDetail> reportDetails = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_33;
        reportDetails.forEach(x -> {
            x.setDescriptionColumn(map.get(x.getColumnNum()));
        });
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-33.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport33PdfManual(long requestReportId) throws Exception {
        List<ReportPeriodDetail> details = findByRequestPeriodReportId(requestReportId);
        RequestPeriodReport requestReport = requestReportRepository.findById(requestReportId).orElse(null);
        if (requestReport == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        if (ArrayListCommon.isNullOrEmpty(details)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<ReportPeriodDetail> reportDetails = new ArrayList<>();
        Map<Integer, String> map = ReportConstant.REQUEST_REPORT.DESCRIPTION_COLUMN.REPORT_33;
        details.forEach(x -> reportDetails.add(new ReportPeriodDetail(x.getColumnNum(), x.getValueManual() != null ? x.getValueManual() : x.getValue(), map.get(x.getColumnNum()))));
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(requestReportId);
        JasperPrint jp;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reportDetails);
            Map<String, Object> parameters = new HashMap<>();
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(requestReport.getCreatedAt());
            int month = DateCommon.monthOfDate(requestReport.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(requestReport.getCreatedAt());
//            String username = cacheService.getUsernameFromHeader();
//            if (username == null) throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng l???y ???????c username");
//            User user = cacheService.getUserFromCache(username);
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
            File file = ResourceUtils.getFile("classpath:request-report/report-33.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    public byte[] requestReport02Pdf(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-02.jrxml");
    }

    public byte[] requestReport02PdfManual(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-02-manual.jrxml");
    }

    public byte[] requestReport03Pdf(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-03.jrxml");
    }

    public byte[] requestReport03PdfManual(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-03-manual.jrxml");
    }

    public byte[] requestReport04Pdf(RequestPeriodReport req) throws Exception {
        if ("1".equals(req.getReportType())) {
            return commonRequestPeriodReport_PDF_Landscape(req, "report-04.jrxml");
        }
        return commonRequestPeriodReport_PDF_Landscape(req, "report-04_spp.jrxml");
    }

    public byte[] requestReport05Pdf(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-05.jrxml");
    }

    public byte[] requestReport05PdfManual(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-05-manual.jrxml");
    }

    public byte[] requestReport06Pdf(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-06.jrxml");
    }

    public byte[] requestReport06PdfManual(RequestPeriodReport req) throws Exception {
        return commonRequestPeriodReport_Pdf(req, "report-06-manual.jrxml");
    }

    public byte[] requestReport10Pdf(RequestPeriodReport req) throws Exception {
        if ("1".equals(req.getReportType())) {
            return commonRequestPeriodReport_PDF_Landscape(req, "report-10.jrxml");
        }
        return commonRequestPeriodReport_PDF_Landscape(req, "report-10_spp.jrxml");
    }

    public byte[] requestReport11Pdf(RequestPeriodReport req) throws Exception {
        if ("1".equals(req.getReportType())) {
            return commonRequestPeriodReport_PDF_Landscape(req, "report-11.jrxml");
        }
        return commonRequestPeriodReport_PDF_Landscape(req, "report-11_spp.jrxml");
    }

    public byte[] requestReport12Pdf(RequestPeriodReport req) throws Exception {
        if ("1".equals(req.getReportType())) {
            return commonRequestPeriodReport_PDF_Landscape(req, "report-12.jrxml");
        }
        return commonRequestPeriodReport_PDF_Landscape(req, "report-12_spp.jrxml");
    }

    public byte[] commonRequestPeriodReport_Pdf(RequestPeriodReport req, String fileName) throws Exception {
        Connection conn = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(req.getId());
        try {
            String dbURL = "jdbc:oracle:thin:@10.47.104.230:1521:QLAHSDC1";
            String user = "SPP_V2";
            String pass = "Ab123456";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            JasperPrint jp = null;
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(req.getCreatedAt());
            int month = DateCommon.monthOfDate(req.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(req.getCreatedAt());
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("SPPID", req.getSppId());
            parameters.put("FROMDATE", DateCommon.convertStringToDateByPattern(req.getFromDate(), "yyyyMMdd"));
            parameters.put("TODATE", DateCommon.convertStringToDateByPattern(req.getToDate(), "yyyyMMdd"));
            parameters.put("REPORTLEVEL", req.getReportLevel());
            parameters.put("REPORTDISP", req.getGenId());
            parameters.put("REPORTTYPE", req.getReportType());
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
//            parameters.put("date", date);
            File file = ResourceUtils.getFile("classpath:request-report/" + fileName);
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, conn);
            return jasperPrintService.exportReportPdf(jp);
        } catch (CommonException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public byte[] commonRequestPeriodReport_PDF_Landscape(RequestPeriodReport req, String fileName) throws Exception {
        Connection conn = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        ReportPeriodInput reportInput = reportInputRepository.findFirstByRequestReportId(req.getId());
        try {
            String dbURL = "jdbc:oracle:thin:@10.47.104.230:1521:QLAHSDC1";
            String user = "SPP_V2";
            String pass = "Ab123456";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            JasperPrint jp = null;
            String fromDate = DateCommon.convertDateTimeToString(reportInput.getFromDate()).substring(9);
            String toDate = DateCommon.convertDateTimeToString(reportInput.getToDate()).substring(9);
            String[] sppId = reportInput.getSppId().split(";");
            String optName = "";
            if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("1")) {
                optName = "S??? li???u t???ng h???p c???a VKS hi???n t???i";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("2")) {
                optName = "S??? li???u t???ng h???p c???a c??c VKS c???p tr???c thu???c";
            } else if (!StringCommon.isNullOrBlank(reportInput.getOption()) && reportInput.getOption().equals("3")) {
                optName = "S??? li???u t???ng h???p c???a 2 c???p";
            } else optName = null;
            int day = DateCommon.dayOfMonth(req.getCreatedAt());
            int month = DateCommon.monthOfDate(req.getCreatedAt()) + 1;
            int year = DateCommon.yearOfDate(req.getCreatedAt());
            String date = "....... ng??y " + day + " th??ng " + month + " n??m " + year;
            String underPos = "";
            if (!StringCommon.isNullOrBlank(reportInput.getPosition())) {
                underPos = "(K??, ghi r?? h??? v?? t??n, ????ng d???u)";
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("SPPID", req.getSppId());
            parameters.put("FROMDATE", DateCommon.convertStringToDateByPattern(req.getFromDate(), "yyyyMMdd"));
            parameters.put("TODATE", DateCommon.convertStringToDateByPattern(req.getToDate(), "yyyyMMdd"));
            parameters.put("REPORTLEVEL", req.getReportLevel());
            parameters.put("REPORTDISP", req.getGenId());
            parameters.put("REPORTTYPE", req.getReportType());
            parameters.put("CODEID", reportInput.getCodeId());
            parameters.put("GROUPID", reportInput.getGroupId());
            parameters.put("LAWID", reportInput.getLawId());
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
            parameters.put("sppId", sppId.length);
            parameters.put("date", date);
            parameters.put("name", reportInput.getCreatedBy() == null ? " " : reportInput.getCreatedBy());
            parameters.put("signature", reportInput.getSignature() == null ? " " : reportInput.getSignature());
            parameters.put("sppname", reportInput.getSppname() == null ? " " : reportInput.getSppname());
            parameters.put("optName", optName);
            parameters.put("opt", reportInput.getOption());
            parameters.put("position", StringCommon.isNullOrBlank(reportInput.getPosition()) ? "" : reportInput.getPosition().toUpperCase());
            parameters.put("underPos", underPos);
//            parameters.put("date", date);
            File file = ResourceUtils.getFile("classpath:request-report/" + fileName);
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, conn);
            return jasperPrintService.exportReportPdf(jp);
        } catch (CommonException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public ReportPeriodDetail updateValueManual(ReportPeriodDetail reportDetail) throws Exception {
        ValidateCommon.validateNullObject(reportDetail.getId(), "id");
        ReportPeriodDetail old = reportDetailRepository.findById(reportDetail.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        old.setValueManual(reportDetail.getValueManual());
        return old;
    }

    @Override
    public RequestPeriodReport changeStatus(String genId, Integer status) throws Exception {
        ValidateCommon.validateNullObject(genId, "genId");
        RequestPeriodReport requestReportOld = requestReportRepository.findFirstByGenId(genId);
        if (requestReportOld == null) {
            throw new CommonException(Response.DATA_NOT_FOUND, "Kh??ng t??m th???y y??u c???u b??o c??o");
        }
        requestReportOld.setStatus(status);
        requestReportOld.setEndAt(new Date());
        return requestReportRepository.save(requestReportOld);
    }
}
