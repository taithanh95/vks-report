package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book16Request;
import com.bitsco.vks.report.response.Book16Response;

import java.util.List;

public interface Book16Service {
    List<Book16Response> querying(Book16Request request) throws Exception;

    byte[] requestReportPdf(Book16Request request) throws Exception;

    byte[] requestReportXlsx(Book16Request request) throws Exception;

    byte[] requestReportDocx(Book16Request request) throws Exception;
}
