package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book15Request;
import com.bitsco.vks.report.response.Book15Response;

import java.util.List;

public interface Book15Service {
    List<Book15Response> querying(Book15Request request) throws Exception;

    byte[] requestReportPdf(Book15Request request) throws Exception;

    byte[] requestReportXlsx(Book15Request request) throws Exception;

    byte[] requestReportDocx(Book15Request request) throws Exception;
}
