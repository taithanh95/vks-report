package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book06Request;
import com.bitsco.vks.report.response.Book06Response;

import java.util.List;

public interface Book06Repository {
    List<Book06Response> querying(Book06Request request) throws Exception;
}
