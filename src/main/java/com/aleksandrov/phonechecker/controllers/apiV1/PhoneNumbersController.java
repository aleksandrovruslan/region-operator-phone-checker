package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.services.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PhoneNumbersController.CHECK_PHONES_MAPPING)
public class PhoneNumbersController {

    public final static String CHECK_PHONES_MAPPING = "/api/v1/numbers";
    public final static String CHECK_PHONES_GET = "/{id}";

    @Autowired
    private CheckService checkService;

    //TODO improve query performance
    @GetMapping(CHECK_PHONES_GET)
    public List<PhoneNumber> get(@PathVariable String id) {
        return checkService.check(id);
    }

}
