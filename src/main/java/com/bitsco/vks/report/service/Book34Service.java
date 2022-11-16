package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book34Request;
import com.bitsco.vks.report.response.Book34Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 10:05 AM
 */
public interface Book34Service {
    List<Book34Response> querying(Book34Request request) throws Exception;

    byte[] requestReportPdf(Book34Request request) throws Exception;

    byte[] requestReportXlsx(Book34Request request) throws Exception;

    byte[] requestReportDocx(Book34Request request) throws Exception;
}
