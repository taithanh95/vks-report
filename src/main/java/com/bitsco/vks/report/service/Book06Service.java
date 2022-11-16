package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book06Request;
import com.bitsco.vks.report.response.Book06Response;

import java.util.List;

public interface Book06Service {
    List<Book06Response> querying(Book06Request request) throws Exception;

    byte[] requestReportPdf(Book06Request request) throws Exception;

    byte[] requestReportXlsx(Book06Request request) throws Exception;

    byte[] requestReportDocx(Book06Request request) throws Exception;
}
