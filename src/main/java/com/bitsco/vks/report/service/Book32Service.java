package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book32Request;
import com.bitsco.vks.report.response.Book32Response;

import java.util.List;

public interface Book32Service {
    List<Book32Response> querying(Book32Request request) throws Exception;

    byte[] requestReportPdf(Book32Request request) throws Exception;

    byte[] requestReportXlsx(Book32Request request) throws Exception;

    byte[] requestReportDocx(Book32Request request) throws Exception;
}
