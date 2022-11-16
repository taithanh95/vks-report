package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book17Request;
import com.bitsco.vks.report.response.Book17Response;

import java.util.List;

public interface Book17Repository {
    List<Book17Response> querying(Book17Request request) throws Exception;
}
