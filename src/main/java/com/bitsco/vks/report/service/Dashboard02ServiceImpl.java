package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.jasper.JasperPrintService;
import com.bitsco.vks.report.repository.Dashboard02Repository;
import com.bitsco.vks.report.request.Dashboard02Request;
import com.bitsco.vks.report.response.Dashboard02Response;
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
public class Dashboard02ServiceImpl implements Dashboard02Service {
    @Autowired
    Dashboard02Repository dashboard02Repository;

    @Autowired
    private JasperPrintService jasperPrintService;

    @Override
    public List<Dashboard02Response> querying(Dashboard02Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getSppId(), "sppId");
        List<Dashboard02Response> responseList = dashboard02Repository.querying(request);
        if (ArrayListCommon.isNullOrEmpty(responseList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return responseList;
    }

    @Override
    public List<Dashboard02Response> detail(Dashboard02Request request) throws Exception {
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        ValidateCommon.validateNullObject(request.getSppId(), "sppId");
        ValidateCommon.validateNullObject(request.getType(), "type");
        List<Dashboard02Response> responseList = dashboard02Repository.detail(request);
        return responseList;
    }

    @Override
    public byte[] requestReportDetail(Dashboard02Request request) throws Exception {
        request.setType(1);
        List<Dashboard02Response> listCapLenhQuyetDinhVuAn = detail(request);
        request.setType(2);
        List<Dashboard02Response> listCapLenhQuyetDinhBiCan = detail(request);
        request.setType(3);
        List<Dashboard02Response> listCapLenhQuyetDinhTinBao = detail(request);
        if (ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhVuAn)
                && ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhBiCan)
                && ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhTinBao))
            throw new CommonException(Response.DATA_NOT_FOUND);
        JasperPrint jp;
        try {
            Map<String, Object> parameters = new HashMap<>();
            if (!ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhVuAn))
                parameters.put("danhSachCapLenhQuyetDinhVuAn", new JRBeanCollectionDataSource(listCapLenhQuyetDinhVuAn));
            if (!ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhBiCan))
                parameters.put("danhSachCapLenhQuyetDinhBiCan", new JRBeanCollectionDataSource(listCapLenhQuyetDinhBiCan));
            if (!ArrayListCommon.isNullOrEmpty(listCapLenhQuyetDinhTinBao))
                parameters.put("danhSachCapLenhQuyetDinhTinBao", new JRBeanCollectionDataSource(listCapLenhQuyetDinhTinBao));
            parameters.put("fromDate", DateCommon.convertDateToString(request.getFromDate()));
            parameters.put("toDate", DateCommon.convertDateToString(request.getToDate()));
            File file = ResourceUtils.getFile("classpath:dashboard/dashboard-02.jrxml");
            InputStream input = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jp = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrintService.exportReportXlsxMultiSheet(jp, new String[] { "Vụ án", "Bị can", "Tin báo" });
        } catch (FileNotFoundException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "FileNotFoundException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        } catch (JRException e) {
            throw new CommonException(Response.SYSTEM_ERROR, "JRException khi requestReportDocx thực hiện tạo JasperPrint. Chi tiết: " + e.getMessage());
        }
    }
}
