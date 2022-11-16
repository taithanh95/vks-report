package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.cache.CacheService;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.repository.Book02Repository;
import com.bitsco.vks.report.request.Book02Request;
import com.bitsco.vks.report.response.Book02Response;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class Book02ServiceImpl implements Book02Service {
    @Autowired
    Book02Repository book02Repository;

    @Autowired
    CacheService cacheService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public List<Book02Response> querying(Book02Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getUnitId(), "unitId");
//        Spp spp = cacheService.getSppFromCache(request.getUnitId());
//        if (spp != null && spp.getIsDePart().equals("Y")) {
//            request.setUnitId(spp.getSppParent());
//        }
        List<Book02Response> responseList = book02Repository.querying(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return responseList;
    }

    @Override
    public byte[] requestReportPdf(Book02Request request) throws Exception {
        Spp spp = cacheService.getSppFromCache(request.getUnitId());
//        if (spp != null && spp.getIsDePart().equals("Y")) {
//            request.setUnitId(spp.getSppParent());
//        }
        List<Book02Response> book02ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book02ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            if (spp != null) {
                //Lay thong tin VKS & VKS cap tren cua tai khoan
                if (spp.getSppParent() != null) {
                    Spp sppParent = cacheService.getSppFromCache(spp.getSppParent());
                    if (sppParent.getSppId().trim().equals(spp.getSppId().trim())) {
                        parameters.put("sppParent", sppParent.getName());
                    } else {
                        parameters.put("spp", spp.getName());
                        parameters.put("sppParent", sppParent.getName());
                    }
                } else {
                    parameters.put("spp", spp.getName());
                }
            }
            File file = ResourceUtils.getFile("classpath:report/book-02.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (CommonException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportXlsx(Book02Request request) throws Exception {
        Spp spp = cacheService.getSppFromCache(request.getUnitId());
//        if (spp != null && spp.getIsDePart().equals("Y")) {
//            request.setUnitId(spp.getSppParent());
//        }
        List<Book02Response> book02ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book02ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            if (spp != null) {
                //Lay thong tin VKS & VKS cap tren cua tai khoan
                if (spp.getSppParent() != null) {
                    Spp sppParent = cacheService.getSppFromCache(spp.getSppParent());
                    if (sppParent.getSppId().trim().equals(spp.getSppId().trim())) {
                        parameters.put("sppParent", sppParent.getName());
                    } else {
                        parameters.put("spp", spp.getName());
                        parameters.put("sppParent", sppParent.getName());
                    }
                } else {
                    parameters.put("spp", spp.getName());
                }
            }
            File file = ResourceUtils.getFile("classpath:report/book-02.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsx(jp, "SỔ-ĐĂNG-KÝ-CÁC-LỆNH-QUYẾT-ĐỊNH-YÊU-CẦU-TRONG-GIAI-ĐOẠN-ĐIỀU-TRA");
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportXlsx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportXlsx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportDocx(Book02Request request) throws Exception {
        Spp spp = cacheService.getSppFromCache(request.getUnitId());
//        if (spp != null && spp.getIsDePart().equals("Y")) {
//            request.setUnitId(spp.getSppParent());
//        }
        List<Book02Response> book02ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book02ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            if (spp != null) {
                //Lay thong tin VKS & VKS cap tren cua tai khoan
                if (spp.getSppParent() != null) {
                    Spp sppParent = cacheService.getSppFromCache(spp.getSppParent());
                    if (sppParent.getSppId().trim().equals(spp.getSppId().trim())) {
                        parameters.put("sppParent", sppParent.getName());
                    } else {
                        parameters.put("spp", spp.getName());
                        parameters.put("sppParent", sppParent.getName());
                    }
                } else {
                    parameters.put("spp", spp.getName());
                }
            }
            File file = ResourceUtils.getFile("classpath:report/book-02.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportDocx(jp);
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }
}
