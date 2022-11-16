package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book33Request;
import com.bitsco.vks.report.response.Book33Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 9:46 AM
 */
public interface Book33Repository {

    List<Book33Response> queryingVcc(Book33Request request) throws Exception;
}
