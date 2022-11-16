package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book10Request;
import com.bitsco.vks.report.response.Book10Response;

import java.util.List;

public interface Book10Service {
    List<Book10Response> querying(Book10Request request) throws Exception;

    byte[] requestReportPdf(Book10Request request) throws Exception;

    byte[] requestReportXlsx(Book10Request request) throws Exception;

    byte[] requestReportDocx(Book10Request request) throws Exception;

    Book10Request createOrUpdateNote(Book10Request noteBook);
}
