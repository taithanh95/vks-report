package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.repository.Dashboard04Repository;
import com.bitsco.vks.report.request.Dashboard04Request;
import com.bitsco.vks.report.response.Dashboard04Response;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class Dashboard04ServiceImpl implements Dashboard04Service {
    @Autowired
    Dashboard04Repository dashboard04Repository;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public List<Dashboard04Response> querying(Dashboard04Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getSppId(), "sppId");
        List<Dashboard04Response> responseList = dashboard04Repository.querying(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        List<Dashboard04Response> response = new ArrayList<>();
        Dashboard04Response giaiDoanG1 = null;
        Dashboard04Response giaiDoanG2 = null;
        Dashboard04Response giaiDoanG3 = null;
        Dashboard04Response giaiDoanG4 = null;
        Dashboard04Response giaiDoanG5 = null;
        for (Dashboard04Response x : responseList) {
            switch (x.getMaGiaiDoan()) {
                case "G1":
                    giaiDoanG1 = x;
                    break;
                case "G2":
                    giaiDoanG2 = x;
                    break;
                case "G3":
                    giaiDoanG3 = x;
                    break;
                case "G4":
                    giaiDoanG4 = x;
                    break;
                default:
                    giaiDoanG5 = x;
            }
        }
        response.add(giaiDoanG1 != null ? giaiDoanG1 : new Dashboard04Response("Điều tra", 0, 0));
        response.add(giaiDoanG2 != null ? giaiDoanG2 : new Dashboard04Response("Truy tố", 0, 0));
        response.add(giaiDoanG3 != null ? giaiDoanG3 : new Dashboard04Response("Sơ thẩm", 0, 0));
        response.add(giaiDoanG4 != null ? giaiDoanG4 : new Dashboard04Response("Phúc thẩm", 0, 0));
        response.add(giaiDoanG5 != null ? giaiDoanG5 : new Dashboard04Response("GĐT/TT", 0, 0));
        return response;
    }

    @Override
    public List<Dashboard04Response> detail(Dashboard04Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getSppId(), "sppId");
        List<Dashboard04Response> responseList = dashboard04Repository.detail(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return responseList;
    }

    @Override
    public byte[] requestReportDetail(Dashboard04Request request) throws Exception {
        List<Dashboard04Response> list = detail(request);
        if (ArrayListCommon.isNullOrEmpty(list))
            throw new CommonException(Response.DATA_NOT_FOUND);
        List<Dashboard04Response> listG1 = new ArrayList<>();
        List<Dashboard04Response> listG2 = new ArrayList<>();
        List<Dashboard04Response> listG3 = new ArrayList<>();
        List<Dashboard04Response> listG4 = new ArrayList<>();
        List<Dashboard04Response> listG5 = new ArrayList<>();
        int sttG1 = 1;
        int sttG2 = 1;
        int sttG3 = 1;
        int sttG4 = 1;
        int sttG5 = 1;
        for (Dashboard04Response x : list) {
            switch (x.getMaGiaiDoan()) {
                case "G1":
                    x.setStt(sttG1++);
                    listG1.add(x);
                    break;
                case "G2":
                    x.setStt(sttG2++);
                    listG2.add(x);
                    break;
                case "G3":
                    x.setStt(sttG3++);
                    listG3.add(x);
                    break;
                case "G4":
                    x.setStt(sttG4++);
                    listG4.add(x);
                    break;
                default:
                    x.setStt(sttG5++);
                    listG5.add(x);
            }
        }
        JasperPrint jp;
        try {
            Map<String, Object> parameters = new HashMap<>();
            if (!ArrayListCommon.isNullOrEmpty(listG1))
                parameters.put("listGiaiDoanDieuTra", new JRBeanCollectionDataSource(listG1));
            if (!ArrayListCommon.isNullOrEmpty(listG2))
                parameters.put("listGiaiDoanTruyTo", new JRBeanCollectionDataSource(listG2));
            if (!ArrayListCommon.isNullOrEmpty(listG3))
                parameters.put("listGiaiDoanXetXuSoTham", new JRBeanCollectionDataSource(listG3));
            if (!ArrayListCommon.isNullOrEmpty(listG4))
                parameters.put("listGiaiDoanXetXuPhucTham", new JRBeanCollectionDataSource(listG4));
            if (!ArrayListCommon.isNullOrEmpty(listG5))
                parameters.put("listGiaiDoanGDTTT", new JRBeanCollectionDataSource(listG5));
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            File file = ResourceUtils.getFile("classpath:dashboard/dashboard-04.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsxMultiSheet(jp, new String[]{"Điều tra", "Truy tố", "Sơ thẩm", "Phúc thẩm", "Giám đốc thẩm/Tái thẩm"});
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }
}
