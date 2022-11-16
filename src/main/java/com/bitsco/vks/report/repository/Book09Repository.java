package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book09Request;
import com.bitsco.vks.report.response.Book09Response;

import java.util.List;

public interface Book09Repository {
    List<Book09Response> querying(Book09Request request) throws Exception;

    Book09Request save(Book09Request note);
}
