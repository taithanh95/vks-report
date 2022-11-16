package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book66Request;
import com.bitsco.vks.report.response.Book66Response;

import java.util.List;

public interface Book66Repository {
    List<Book66Response> querying(Book66Request request) throws Exception;
}
