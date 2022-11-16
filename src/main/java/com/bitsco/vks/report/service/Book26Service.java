package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book26Request;
import com.bitsco.vks.report.response.Book26Response;

import java.util.List;

public interface Book26Service {
    List<Book26Response> querying(Book26Request request) throws Exception;

    byte[] requestReportPdf(Book26Request request) throws Exception;

    byte[] requestReportXlsx(Book26Request request) throws Exception;

    byte[] requestReportDocx(Book26Request request) throws Exception;
}
