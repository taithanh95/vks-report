package com.bitsco.vks.report.service;

import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.request.UserRequest;

public interface CategoryService {
    Spp findFirstByUsername(UserRequest userRequest) throws Exception;

    Spp findByParent(Spp spp) throws Exception;
}
