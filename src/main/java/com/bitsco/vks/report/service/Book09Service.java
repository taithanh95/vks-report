package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book09Request;
import com.bitsco.vks.report.response.Book09Response;

import java.util.List;

public interface Book09Service {
    List<Book09Response> querying(Book09Request request) throws Exception;

    byte[] requestReportPdf(Book09Request request) throws Exception;

    byte[] requestReportXlsx(Book09Request request) throws Exception;

    byte[] requestReportDocx(Book09Request request) throws Exception;

    Book09Request createOrUpdateNote(Book09Request noteBook);
}
