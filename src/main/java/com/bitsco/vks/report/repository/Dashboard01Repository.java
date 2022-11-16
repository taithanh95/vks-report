package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Dashboard01Request;
import com.bitsco.vks.report.response.Dashboard01Response;

import java.util.List;

public interface Dashboard01Repository {
    List<Dashboard01Response> querying(Dashboard01Request request) throws Exception;

    List<Dashboard01Response> detail(Dashboard01Request request) throws Exception;
}
