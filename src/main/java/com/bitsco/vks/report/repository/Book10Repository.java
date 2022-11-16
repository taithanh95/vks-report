package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book10Request;
import com.bitsco.vks.report.response.Book10Response;

import java.util.List;

public interface Book10Repository {
    List<Book10Response> querying(Book10Request request) throws Exception;
    Book10Request save(Book10Request note);
}
