package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Dashboard03Request;
import com.bitsco.vks.report.response.Dashboard03Response;

import java.util.List;

public interface Dashboard03Repository {
    List<Dashboard03Response> querying(Dashboard03Request request) throws Exception;

    List<Dashboard03Response> detail(Dashboard03Request request) throws Exception;
}
