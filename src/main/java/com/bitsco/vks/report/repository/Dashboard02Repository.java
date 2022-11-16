package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Dashboard02Request;
import com.bitsco.vks.report.response.Dashboard02Response;

import java.util.List;

public interface Dashboard02Repository {
    List<Dashboard02Response> querying(Dashboard02Request request) throws Exception;

    List<Dashboard02Response> detail(Dashboard02Request request) throws Exception;
}
