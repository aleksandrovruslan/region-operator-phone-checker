package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.services.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PhoneNumberController.CHECK_PHONE_MAPPING)
public class PhoneNumberController {
    public final static String CHECK_PHONE_MAPPING = "/api/v1/number";
    public final static String CHECK_PHONE_GET = "/{id}";

    @Autowired
    private CheckService checkService;

    @GetMapping(CHECK_PHONE_GET)
    public PhoneNumber get(@PathVariable String id) {
        return checkService.check(id);
    }
}
