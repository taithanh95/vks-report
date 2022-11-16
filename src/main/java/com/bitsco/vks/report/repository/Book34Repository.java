package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.Book34Request;
import com.bitsco.vks.report.response.Book34Response;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 9:46 AM
 */
public interface Book34Repository {

    List<Book34Response> queryingVcc(Book34Request request) throws Exception;
}
