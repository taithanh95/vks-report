package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book02Request;
import com.bitsco.vks.report.response.Book02Response;

import java.util.List;

public interface Book02Repository {
    List<Book02Response> querying(Book02Request request) throws Exception;
}
