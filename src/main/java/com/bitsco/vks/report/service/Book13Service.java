package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book13Request;
import com.bitsco.vks.report.response.Book13Response;

import java.util.List;

public interface Book13Service {
    List<Book13Response> querying(Book13Request request) throws Exception;

    byte[] requestReportPdf(Book13Request request) throws Exception;

    byte[] requestReportXlsx(Book13Request request) throws Exception;

    byte[] requestReportDocx(Book13Request request) throws Exception;
}
