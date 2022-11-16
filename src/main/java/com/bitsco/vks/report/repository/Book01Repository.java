package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book01Request;
import com.bitsco.vks.report.response.Book01Response;

import java.util.List;

public interface Book01Repository {
    List<Book01Response> querying(Book01Request request) throws Exception;
}
