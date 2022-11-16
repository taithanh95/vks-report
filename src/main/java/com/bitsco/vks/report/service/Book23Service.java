package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book23Request;
import com.bitsco.vks.report.response.Book23Response;

import java.util.List;

public interface Book23Service {
    List<Book23Response> querying(Book23Request request) throws Exception;

    byte[] requestReportPdf(Book23Request request) throws Exception;

    byte[] requestReportXlsx(Book23Request request) throws Exception;

    byte[] requestReportDocx(Book23Request request) throws Exception;
}
