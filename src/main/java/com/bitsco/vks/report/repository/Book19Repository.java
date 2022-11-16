package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book19Request;
import com.bitsco.vks.report.response.Book19Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Ba Tuan Anh <anhnbt.it@gmail.com>
 * Date: 8/9/2021
 * Time: 11:48 AM
 */

public interface Book19Repository {
    List<Book19Response> querying(Book19Request request) throws Exception;
}
