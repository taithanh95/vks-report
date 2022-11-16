package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book33Request;
import com.bitsco.vks.report.response.Book33Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 10:05 AM
 */
public interface Book33Service {
    List<Book33Response> querying(Book33Request request) throws Exception;

    byte[] requestReportPdf(Book33Request request) throws Exception;

    byte[] requestReportXlsx(Book33Request request) throws Exception;

    byte[] requestReportDocx(Book33Request request) throws Exception;
}
