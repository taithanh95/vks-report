package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book36Request;
import com.bitsco.vks.report.response.Book36Response;

import java.util.List;

public interface Book36Repository {
    List<Book36Response> queryingVcc(Book36Request request) throws Exception;
}
