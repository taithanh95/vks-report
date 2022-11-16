package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book03Request;
import com.bitsco.vks.report.response.Book03Response;

import java.util.List;

public interface Book03Repository {
    List<Book03Response> querying(Book03Request request) throws Exception;
}
