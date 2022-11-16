package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book21Request;
import com.bitsco.vks.report.response.Book21Response;

import java.util.List;

public interface Book21Service {
    List<Book21Response> querying(Book21Request request) throws Exception;

    byte[] requestReportPdf(Book21Request request) throws Exception;

    byte[] requestReportXlsx(Book21Request request) throws Exception;

    byte[] requestReportDocx(Book21Request request) throws Exception;
}
