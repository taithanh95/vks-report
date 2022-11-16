package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book35Request;
import com.bitsco.vks.report.response.Book35Response;

import java.util.List;

public interface Book35Service {
    List<Book35Response> querying(Book35Request request) throws Exception;

    byte[] requestReportPdf(Book35Request request) throws Exception;

    byte[] requestReportXlsx(Book35Request request) throws Exception;

    byte[] requestReportDocx(Book35Request request) throws Exception;
}
