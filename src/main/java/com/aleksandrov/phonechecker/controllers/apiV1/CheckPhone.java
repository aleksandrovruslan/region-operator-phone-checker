package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.services.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CheckPhone.CHECK_PHONE_MAPPING)
public class CheckPhone {
    public final static String CHECK_PHONE_MAPPING = "/api/v1/check/";
    public final static String CHECK_PHONE_GET = "number/";
    public final static String CHECK_PHONES = "numbers/";

    @Autowired
    private CheckService checkService;

    @PostMapping(CheckPhone.CHECK_PHONE_GET)
    public PhoneNumber get(@RequestBody PhoneNumber number) {
        return checkService.check(number);
    }

    @PostMapping(CheckPhone.CHECK_PHONES)
    public List<PhoneNumber> getPhones(@RequestBody List<PhoneNumber> numbers) {
        return checkService.checkAll(numbers);
    }
}
