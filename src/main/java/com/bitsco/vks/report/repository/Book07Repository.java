package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book07Request;
import com.bitsco.vks.report.response.Book07Response;

import java.util.List;

public interface Book07Repository {
    List<Book07Response> querying(Book07Request request) throws Exception;

    List<Book07Response> queryingOneYear(Book07Request reques) throws Exception;

    Book07Request save(Book07Request note);
}
