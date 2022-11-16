package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book17Request;
import com.bitsco.vks.report.response.Book17Response;

import java.util.List;

public interface Book17Service {
    List<Book17Response> querying(Book17Request request) throws Exception;

    byte[] requestReportPdf(Book17Request request) throws Exception;

    byte[] requestReportXlsx(Book17Request request) throws Exception;

    byte[] requestReportDocx(Book17Request request) throws Exception;
}
