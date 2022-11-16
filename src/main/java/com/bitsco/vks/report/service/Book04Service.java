package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book04Request;
import com.bitsco.vks.report.response.Book04Response;

import java.util.List;

public interface Book04Service {
    List<Book04Response> querying(Book04Request request) throws Exception;

    byte[] requestReportPdf(Book04Request request) throws Exception;

    byte[] requestReportXlsx(Book04Request request) throws Exception;

    byte[] requestReportDocx(Book04Request request) throws Exception;
}
