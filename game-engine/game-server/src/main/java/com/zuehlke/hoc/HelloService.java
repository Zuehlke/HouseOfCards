package com.zuehlke.hoc;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class HelloService {
    private static final Logger log = LoggerFactory.getLogger(HelloService.class.getName());

    public void hello() {
        log.info("Hello from HelloService");
    }
}
