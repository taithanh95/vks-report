package com.bitsco.vks.report.service;

import com.bitsco.vks.report.request.Dashboard01Request;
import com.bitsco.vks.report.response.Dashboard01Response;

import java.util.List;

public interface Dashboard01Service {
    List<Dashboard01Response> querying(Dashboard01Request request) throws Exception;

    List<Dashboard01Response> detail(Dashboard01Request request) throws Exception;

    byte[] requestReportDetail(Dashboard01Request request) throws Exception;
}
