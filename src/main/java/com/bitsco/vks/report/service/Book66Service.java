package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book66Request;
import com.bitsco.vks.report.response.Book66Response;

import java.util.List;

public interface Book66Service {
    List<Book66Response> querying(Book66Request request) throws Exception;

    byte[] requestReportPdf(Book66Request request) throws Exception;

    byte[] requestReportXlsx(Book66Request request) throws Exception;

    byte[] requestReportDocx(Book66Request request) throws Exception;
}
