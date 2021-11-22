package com.backbase.dbs.payment.batch.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RtnBlocklistValidator {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isValid(String rtnBlocklistUrl, String rtn) {
        if (rtnBlocklistUrl == null) {
            return false;
        }

        RtnBlocklist blocklist = restTemplate.getForObject(rtnBlocklistUrl, RtnBlocklist.class);

        if (blocklist == null) {
            return false;
        }

        return !blocklist.contains(rtn);
    }
}
