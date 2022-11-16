package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book25Request;
import com.bitsco.vks.report.response.Book25Response;

import java.util.List;

public interface Book25Repository {
    List<Book25Response> querying(Book25Request request) throws Exception;
}
