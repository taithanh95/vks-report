package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book35Request;
import com.bitsco.vks.report.response.Book35Response;

import java.util.List;

public interface Book35Repository {

    List<Book35Response> queryingVcc(Book35Request request) throws Exception;
}
