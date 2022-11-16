package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Dashboard02Request;
import com.bitsco.vks.report.response.Dashboard02Response;

import java.util.List;

public interface Dashboard02Service {
    List<Dashboard02Response> querying(Dashboard02Request request) throws Exception;

    List<Dashboard02Response> detail(Dashboard02Request request) throws Exception;

    byte[] requestReportDetail(Dashboard02Request request) throws Exception;
}
