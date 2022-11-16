package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book03Request;
import com.bitsco.vks.report.response.Book03Response;

import java.util.List;

public interface Book03Service {
    List<Book03Response> querying(Book03Request request) throws Exception;

    byte[] requestReportPdf(Book03Request request) throws Exception;

    byte[] requestReportXlsx(Book03Request request) throws Exception;

    byte[] requestReportDocx(Book03Request request) throws Exception;
}
