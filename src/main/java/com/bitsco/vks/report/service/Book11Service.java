package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book11Request;
import com.bitsco.vks.report.response.Book11Response;

import java.util.List;

public interface Book11Service {
    List<Book11Response> querying(Book11Request request) throws Exception;

    byte[] requestReportPdf(Book11Request request) throws Exception;

    byte[] requestReportXlsx(Book11Request request) throws Exception;

    byte[] requestReportDocx(Book11Request request) throws Exception;
}
