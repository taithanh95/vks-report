package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book08Request;
import com.bitsco.vks.report.response.Book08Response;

import java.util.List;

public interface Book08Repository {
    List<Book08Response> querying(Book08Request request) throws Exception;

    List<Book08Response> queryingOneYear(Book08Request request) throws Exception;

    Book08Request save(Book08Request note);
}
