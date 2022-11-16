package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book08Request;
import com.bitsco.vks.report.response.Book08Response;

import java.util.List;

public interface Book08Service {
    List<Book08Response> querying(Book08Request request) throws Exception;

    List<Book08Response> queryingOneYear(Book08Request request) throws Exception;

    byte[] requestReportPdf(Book08Request request) throws Exception;

    byte[] requestReportXlsx(Book08Request request) throws Exception;

    byte[] requestReportDocx(Book08Request request) throws Exception;

    byte[] requestReportPdfOneYear(Book08Request request) throws Exception;

    byte[] requestReportXlsxOneYear(Book08Request request) throws Exception;

    byte[] requestReportDocxOneYear(Book08Request request) throws Exception;

    Book08Request createOrUpdateNote(Book08Request noteBook);
}
