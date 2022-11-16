package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book30Request;
import com.bitsco.vks.report.response.Book30Response;

import java.util.List;

public interface Book30Repository {
    List<Book30Response> querying(Book30Request request) throws Exception;
}
