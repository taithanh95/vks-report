package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book05Request;
import com.bitsco.vks.report.response.Book05Response;

import java.util.List;

public interface Book05Service {
    List<Book05Response> querying(Book05Request request) throws Exception;

    byte[] requestReportPdf(Book05Request request) throws Exception;

    byte[] requestReportXlsx(Book05Request request) throws Exception;

    byte[] requestReportDocx(Book05Request request) throws Exception;
}
