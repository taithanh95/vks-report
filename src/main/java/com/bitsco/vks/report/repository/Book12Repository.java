package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book12Request;
import com.bitsco.vks.report.response.Book12Response;

import java.util.List;

public interface Book12Repository {
    List<Book12Response> querying(Book12Request request) throws Exception;
}
