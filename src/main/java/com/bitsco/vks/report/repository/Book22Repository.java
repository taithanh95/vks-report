package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book22Request;
import com.bitsco.vks.report.response.Book22Response;

import java.util.List;

public interface Book22Repository {
    List<Book22Response> querying(Book22Request book22Request) throws Exception;
}
