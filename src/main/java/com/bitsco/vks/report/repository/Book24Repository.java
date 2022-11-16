package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book24Request;
import com.bitsco.vks.report.response.Book24Response;

import java.util.List;

public interface Book24Repository {
    List<Book24Response> querying(Book24Request request) throws Exception;
}
