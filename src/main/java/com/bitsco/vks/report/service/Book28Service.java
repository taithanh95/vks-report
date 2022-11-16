package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book28Request;
import com.bitsco.vks.report.response.Book28Response;

import java.util.List;

public interface Book28Service {
    List<Book28Response> querying(Book28Request request) throws Exception;

    byte[] requestReportPdf(Book28Request request) throws Exception;

    byte[] requestReportXlsx(Book28Request request) throws Exception;

    byte[] requestReportDocx(Book28Request request) throws Exception;
}
