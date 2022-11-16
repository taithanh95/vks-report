package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book21Request;
import com.bitsco.vks.report.response.Book21Response;

import java.util.List;

public interface Book21Repository {
    List<Book21Response> querying(Book21Request request) throws Exception;
}
