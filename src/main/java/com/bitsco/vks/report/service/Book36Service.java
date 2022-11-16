package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book36Request;
import com.bitsco.vks.report.response.Book36Response;

import java.util.List;

public interface Book36Service {
    List<Book36Response> querying(Book36Request request) throws Exception;

    byte[] requestReportPdf(Book36Request request) throws Exception;

    byte[] requestReportXlsx(Book36Request request) throws Exception;

    byte[] requestReportDocx(Book36Request request) throws Exception;
}
