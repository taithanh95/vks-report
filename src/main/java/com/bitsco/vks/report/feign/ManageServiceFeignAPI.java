package com.bitsco.vks.report.feign;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.request.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(Constant.FEIGN_CLIENT.MANAGE)
public interface ManageServiceFeignAPI {
    @GetMapping(value = "ping")
    String ping();

    @PostMapping("/spp/findFirstByUsername/")
    ResponseBody findFirstByUsername(@RequestBody UserRequest userRequest);

    @PostMapping("/spp/findByParent/")
    ResponseBody findByParent(@RequestBody Spp spp);

}
