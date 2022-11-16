package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book27Request;
import com.bitsco.vks.report.response.Book27Response;

import java.util.List;

public interface Book27Service {
    List<Book27Response> querying(Book27Request request) throws Exception;

    byte[] requestReportPdf(Book27Request request) throws Exception;

    byte[] requestReportXlsx(Book27Request request) throws Exception;

    byte[] requestReportDocx(Book27Request request) throws Exception;
}
