package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book30Request;
import com.bitsco.vks.report.response.Book30Response;

import java.util.List;

public interface Book30Service {
    List<Book30Response> querying(Book30Request request) throws Exception;

    byte[] requestReportPdf(Book30Request request) throws Exception;

    byte[] requestReportXlsx(Book30Request request) throws Exception;

    byte[] requestReportDocx(Book30Request request) throws Exception;
}
