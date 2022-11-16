package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book11Request;
import com.bitsco.vks.report.response.Book11Response;

import java.util.List;

public interface Book11Repository {
    List<Book11Response> querying(Book11Request request) throws Exception;
}
