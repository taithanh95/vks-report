package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.cache.CacheService;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.repository.Book10Repository;
import com.bitsco.vks.report.request.Book10Request;
import com.bitsco.vks.report.response.Book10Response;
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
public class Book10ServiceImpl implements Book10Service {
    @Autowired
    Book10Repository book10Repository;

    @Autowired
    CacheService cacheService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public List<Book10Response> querying(Book10Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getUnitId(), "unitId");
        List<Book10Response> responseList = book10Repository.querying(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return responseList;
    }

    @Override
    public byte[] requestReportPdf(Book10Request request) throws Exception {
        List<Book10Response> book10ResponseList = querying(request);
        JasperPrint jp;
        try {
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Integer year = DateCommon.getYear();
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book10ResponseList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-10.jrxml");
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
    public byte[] requestReportXlsx(Book10Request request) throws Exception {
        List<Book10Response> book10ResponseList = querying(request);
        JasperPrint jp;
        try {
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Integer year = DateCommon.getYear();
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book10ResponseList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-10.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsx(jp, "SỐ-QUẢN-LÝ-ÁN-HÌNH-SỰ-TRẢ-HỒ-SƠ-ĐIỀU-TRA-BỔ-SUNG");
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportXlsx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportXlsx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportDocx(Book10Request request) throws Exception {
        List<Book10Response> book10ResponseList = querying(request);
        JasperPrint jp;
        try {
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Integer year = DateCommon.getYear();
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book10ResponseList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-10.jrxml");
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

    @Transactional
    @Override
    public Book10Request createOrUpdateNote(Book10Request noteBook) {
        Book10Request note = new Book10Request();
        if (!StringCommon.isNullOrBlank(noteBook.getNote()) && noteBook.getNote().length() > 200) {
            throw new CommonException(Response.REQUIRED_MAX_LENGTH, "Ghi chú vượt quá 200 ký tự");
        }


        if (note == null) {
            return saveNote(noteBook);
        } else return saveNote(note.coppyFrom(noteBook));
    }

    private Book10Request saveNote(Book10Request note) {
        return book10Repository.save(note);
    }
}
