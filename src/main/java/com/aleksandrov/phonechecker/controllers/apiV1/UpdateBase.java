package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.utils.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UpdateBase {
    public static final String UPDATE_BASE = "/api/v1/update";
    @Autowired
    private Downloader downloader;

    // TODO make a check by login and password, or automatically check changes on the site https://rossvyaz.ru
    @GetMapping(UpdateBase.UPDATE_BASE)
    public String update() {
        try {
            downloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Updated successfully.";
    }
}
