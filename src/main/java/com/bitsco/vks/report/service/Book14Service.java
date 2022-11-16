package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book14Request;
import com.bitsco.vks.report.response.Book14Response;

import java.util.List;

public interface Book14Service {
    List<Book14Response> querying(Book14Request request) throws Exception;

    byte[] requestReportPdf(Book14Request request) throws Exception;

    byte[] requestReportXlsx(Book14Request request) throws Exception;

    byte[] requestReportDocx(Book14Request request) throws Exception;
}
