package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book01Request;
import com.bitsco.vks.report.response.Book01Response;

import java.util.List;

public interface Book01Service {
    List<Book01Response> querying(Book01Request request) throws Exception;

    byte[] requestReportPdf(Book01Request request) throws Exception;

    byte[] requestReportXlsx(Book01Request request) throws Exception;

    byte[] requestReportDocx(Book01Request request) throws Exception;
}
