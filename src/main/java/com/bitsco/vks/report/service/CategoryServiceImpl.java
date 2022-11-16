package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.feign.ManageServiceFeignAPI;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    ManageServiceFeignAPI manageServiceFeignAPI;


    @Override
    public Spp findFirstByUsername(UserRequest userRequest) throws Exception {
        ResponseBody responseBody = manageServiceFeignAPI.findFirstByUsername(userRequest);
        if (responseBody == null || StringCommon.isNullOrBlank(responseBody.getResponseCode()) || responseBody.getResponseData() == null)
            throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện truy vấn thông tin vks của tài khoản trực thuộc");
        return (new ObjectMapper()).convertValue(responseBody.getResponseData(), Spp.class);
    }

    @Override
    public Spp findByParent(Spp spp) throws Exception {
        ResponseBody responseBody = manageServiceFeignAPI.findByParent(spp);
        if (responseBody == null || StringCommon.isNullOrBlank(responseBody.getResponseCode()) || responseBody.getResponseData() == null)
            throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện truy vấn thông tin vks cấp trên");
        return (new ObjectMapper()).convertValue(responseBody.getResponseData(), Spp.class);
    }
}
