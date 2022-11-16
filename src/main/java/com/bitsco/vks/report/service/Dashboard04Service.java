package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Dashboard04Request;
import com.bitsco.vks.report.response.Dashboard04Response;

import java.util.List;

public interface Dashboard04Service {
    List<Dashboard04Response> querying(Dashboard04Request request) throws Exception;

    List<Dashboard04Response> detail(Dashboard04Request request) throws Exception;

    byte[] requestReportDetail(Dashboard04Request request) throws Exception;
}
