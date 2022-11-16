package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book23Request;
import com.bitsco.vks.report.response.Book23Response;

import java.util.List;

public interface Book23Repository {
    List<Book23Response> querying(Book23Request request) throws Exception;
}
