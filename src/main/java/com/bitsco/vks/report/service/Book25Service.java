package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book25Request;
import com.bitsco.vks.report.response.Book25Response;

import java.util.List;

public interface Book25Service {
    List<Book25Response> querying(Book25Request request) throws Exception;

    byte[] requestReportPdf(Book25Request request) throws Exception;

    byte[] requestReportXlsx(Book25Request request) throws Exception;

    byte[] requestReportDocx(Book25Request request) throws Exception;
}
