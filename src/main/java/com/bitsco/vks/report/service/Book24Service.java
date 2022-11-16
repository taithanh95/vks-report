package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book24Request;
import com.bitsco.vks.report.response.Book24Response;

import java.util.List;

public interface Book24Service {
    List<Book24Response> querying(Book24Request request) throws Exception;

    byte[] requestReportPdf(Book24Request request) throws Exception;

    byte[] requestReportXlsx(Book24Request request) throws Exception;

    byte[] requestReportDocx(Book24Request request) throws Exception;
}
