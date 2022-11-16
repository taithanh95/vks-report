package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book27Request;
import com.bitsco.vks.report.response.Book27Response;

import java.util.List;

public interface Book27Repository {
    List<Book27Response> querying(Book27Request request) throws Exception;
}
