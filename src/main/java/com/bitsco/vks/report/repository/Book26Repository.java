package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book26Request;
import com.bitsco.vks.report.response.Book26Response;

import java.util.List;

public interface Book26Repository {
    List<Book26Response> querying(Book26Request request) throws Exception;
}
