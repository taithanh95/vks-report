package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book07Request;
import com.bitsco.vks.report.response.Book07Response;

import java.util.List;

public interface Book07Service {
    List<Book07Response> querying(Book07Request request) throws Exception;

    List<Book07Response> queryingOneYear(Book07Request request) throws Exception;

    byte[] requestReportPdf(Book07Request request) throws Exception;

    byte[] requestReportXlsx(Book07Request request) throws Exception;

    byte[] requestReportDocx(Book07Request request) throws Exception;

    byte[] requestReportPdfOneYear(Book07Request request) throws Exception;

    byte[] requestReportXlsxOneYear(Book07Request request) throws Exception;

    byte[] requestReportDocxOneYear(Book07Request request) throws Exception;

    Book07Request createOrUpdateNote(Book07Request noteBook);
}
