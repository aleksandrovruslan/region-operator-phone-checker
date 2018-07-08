package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.services.Downloader;
import com.aleksandrov.phonechecker.services.ReaderCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AdminController {
    public static final String UPDATE_BASE = "/api/v1/update";
    @Autowired
    private Downloader downloader;
    @Autowired
    private ReaderCSV readerCSV;

    // TODO make a check by login and password, or automatically check changes on the site https://rossvyaz.ru
    @GetMapping(AdminController.UPDATE_BASE)
    public String update() {
        try {
            downloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readerCSV.read();
    }
}