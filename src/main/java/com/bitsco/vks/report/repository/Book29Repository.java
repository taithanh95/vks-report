package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book29Request;
import com.bitsco.vks.report.response.Book29Response;

import java.util.List;

public interface Book29Repository {
    List<Book29Response> querying(Book29Request request) throws Exception;
}
