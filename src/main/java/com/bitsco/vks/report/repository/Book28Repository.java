package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book28Request;
import com.bitsco.vks.report.response.Book28Response;

import java.util.List;

public interface Book28Repository {
    List<Book28Response> querying(Book28Request request) throws Exception;
}
