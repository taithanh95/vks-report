package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book14Request;
import com.bitsco.vks.report.response.Book14Response;

import java.util.List;

public interface Book14Repository {
    List<Book14Response> querying(Book14Request request) throws Exception;
}
