package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book05Request;
import com.bitsco.vks.report.response.Book05Response;

import java.util.List;

public interface Book05Repository {
    List<Book05Response> querying(Book05Request request) throws Exception;
}
