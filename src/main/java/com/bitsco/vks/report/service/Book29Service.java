package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book29Request;
import com.bitsco.vks.report.response.Book29Response;

import java.util.List;

public interface Book29Service {
    List<Book29Response> querying(Book29Request request) throws Exception;

    byte[] requestReportPdf(Book29Request request) throws Exception;

    byte[] requestReportXlsx(Book29Request request) throws Exception;

    byte[] requestReportDocx(Book29Request request) throws Exception;
}
