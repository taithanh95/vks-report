package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Dashboard03Request;
import com.bitsco.vks.report.response.Dashboard03Response;

import java.util.List;

public interface Dashboard03Service {
    List<Dashboard03Response> querying(Dashboard03Request request) throws Exception;

    List<Dashboard03Response> detail(Dashboard03Request request) throws Exception;

    byte[] requestReportDetail(Dashboard03Request request) throws Exception;
}
