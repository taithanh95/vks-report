package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.cache.CacheService;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.repository.Book23Repository;
import com.bitsco.vks.report.request.Book23Request;
import com.bitsco.vks.report.response.Book23Response;
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
public class Book23ServiceImpl implements Book23Service {
    @Autowired
    Book23Repository book23Repository;

    @Autowired
    CacheService cacheService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public List<Book23Response> querying(Book23Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getUnitId(), "unitId");
        List<Book23Response> responseList = book23Repository.querying(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return responseList;
    }

    @Override
    public byte[] requestReportPdf(Book23Request request) throws Exception {
        List<Book23Response> book23ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book23ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-23.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportPdf(jp);
        } catch (CommonException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportPdf th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportXlsx(Book23Request request) throws Exception {
        List<Book23Response> book23ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book23ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-23.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsx(jp, "S???-THEO-D??I-VI???C-RA-QUY???T-?????NH-THI-H??NH-??N-C???A-T??A-??N");
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportXlsx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportXlsx th???c hi???n t???o JasperPrint. Chi ti???t: " + e.getMessage());
        }
    }

    @Override
    public byte[] requestReportDocx(Book23Request request) throws Exception {
        List<Book23Response> book23ResponseList = querying(request);
        JasperPrint jp = null;
        try {
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(book23ResponseList);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Integer year = DateCommon.getYear();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("year", year);
            //Lay thong tin VKS & VKS cap tren cua tai khoan
            Spp spp = cacheService.getSppFromCache(request.getUnitId());
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
            File file = ResourceUtils.getFile("classpath:report/book-23.jrxml");
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
}
