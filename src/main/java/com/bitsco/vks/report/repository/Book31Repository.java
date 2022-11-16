package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book31Request;
import com.bitsco.vks.report.response.Book31Response;

import java.util.List;

public interface Book31Repository {
    List<Book31Response> querying(Book31Request request) throws Exception;
}
