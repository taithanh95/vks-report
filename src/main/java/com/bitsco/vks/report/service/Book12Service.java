package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book12Request;
import com.bitsco.vks.report.response.Book12Response;

import java.util.List;

public interface Book12Service {
    List<Book12Response> querying(Book12Request request) throws Exception;

    byte[] requestReportPdf(Book12Request request) throws Exception;

    byte[] requestReportXlsx(Book12Request request) throws Exception;

    byte[] requestReportDocx(Book12Request request) throws Exception;
}
