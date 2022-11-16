package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book20Request;
import com.bitsco.vks.report.response.Book20Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Ba Tuan Anh <anhnbt.it@gmail.com>
 * Date: 8/9/2021
 * Time: 11:48 AM
 */

public interface Book20Repository {
    List<Book20Response> querying(Book20Request request) throws Exception;
}
