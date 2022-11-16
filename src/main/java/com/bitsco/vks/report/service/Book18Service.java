package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book18Request;
import com.bitsco.vks.report.response.Book18Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 06/18/2022
 * Time: 3:57 PM
 */
public interface Book18Service {
    List<Book18Response> querying(Book18Request request) throws Exception;

    byte[] requestReportPdf(Book18Request request) throws Exception;

    byte[] requestReportXlsx(Book18Request request) throws Exception;

    byte[] requestReportDocx(Book18Request request) throws Exception;
}
