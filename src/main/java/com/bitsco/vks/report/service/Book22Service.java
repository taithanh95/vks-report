package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book22Request;
import com.bitsco.vks.report.response.Book22Response;

import java.util.List;

public interface Book22Service {
    List<Book22Response> querying(Book22Request request) throws Exception;

    byte[] requestReportPdf(Book22Request request) throws Exception;

    byte[] requestReportXlsx(Book22Request request) throws Exception;

    byte[] requestReportDocx(Book22Request request) throws Exception;
}
