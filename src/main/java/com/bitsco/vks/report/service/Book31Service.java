package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book31Request;
import com.bitsco.vks.report.response.Book31Response;

import java.util.List;

public interface Book31Service {
    List<Book31Response> querying(Book31Request request) throws Exception;

    byte[] requestReportPdf(Book31Request request) throws Exception;

    byte[] requestReportXlsx(Book31Request request) throws Exception;

    byte[] requestReportDocx(Book31Request request) throws Exception;
}
