package com.bitsco.vks.report.api;

import com.bitsco.vks.common.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 28-Nov-18
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
@CrossOrigin
@RestController
@RequestMapping("ping")
@RefreshScope
public class PingController {
    @Autowired
    private Environment environment;

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @GetMapping
    public ResponseEntity<?> ping() throws Exception {
        return new ResponseEntity<String>(environment.getRequiredProperty("spring.application.name"), HttpStatus.OK);
    }
}
