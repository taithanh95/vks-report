package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book16Request;
import com.bitsco.vks.report.response.Book16Response;

import java.util.List;

public interface Book16Repository {
    List<Book16Response> querying(Book16Request request) throws Exception;

    List<Book16Response> queryingVcc(Book16Request request) throws Exception;
}
