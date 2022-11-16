package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book32Request;
import com.bitsco.vks.report.response.Book32Response;

import java.util.List;

public interface Book32Repository {
    List<Book32Response> querying(Book32Request request) throws Exception;
}
