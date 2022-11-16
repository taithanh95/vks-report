package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book02Request;
import com.bitsco.vks.report.response.Book02Response;

import java.util.List;

public interface Book02Service {
    List<Book02Response> querying(Book02Request request) throws Exception;

    byte[] requestReportPdf(Book02Request request) throws Exception;

    byte[] requestReportXlsx(Book02Request request) throws Exception;

    byte[] requestReportDocx(Book02Request request) throws Exception;
}
