package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book15Request;
import com.bitsco.vks.report.request.Book16Request;
import com.bitsco.vks.report.response.Book15Response;
import com.bitsco.vks.report.response.Book16Response;

import java.util.List;

public interface Book15Repository {
    List<Book15Response> querying(Book15Request request) throws Exception;

    List<Book15Response> queryingVcc(Book15Request request) throws Exception;
}
