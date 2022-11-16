package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book18Request;
import com.bitsco.vks.report.response.Book18Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 06/01/2022
 * Time: 3:51 PM
 */
public interface Book18Repository {
    List<Book18Response> querying(Book18Request request) throws Exception;
}
