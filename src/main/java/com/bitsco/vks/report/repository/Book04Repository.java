package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book04Request;
import com.bitsco.vks.report.response.Book04Response;

import java.util.List;

public interface Book04Repository {
    List<Book04Response> querying(Book04Request request) throws Exception;
}
