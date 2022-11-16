package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Book19Request;
import com.bitsco.vks.report.response.Book19Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Ba Tuan Anh <anhnbt.it@gmail.com>
 * Date: 8/9/2021
 * Time: 11:44 AM
 */

public interface Book19Service {
    List<Book19Response> querying(Book19Request request) throws Exception;

    byte[] requestReportPdf(Book19Request request) throws Exception;

    byte[] requestReportXlsx(Book19Request request) throws Exception;

    byte[] requestReportDocx(Book19Request request) throws Exception;
}
