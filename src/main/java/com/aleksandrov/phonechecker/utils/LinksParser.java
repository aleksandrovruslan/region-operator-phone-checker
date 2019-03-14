package com.aleksandrov.phonechecker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
public class LinksParser {

    @Autowired
    private ConfigProperties properties;
    @Autowired
    private StringsExtractor extractor;

    public Queue<String> getLinks() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new URL(properties.getDownloadPage()).openStream()))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractor.extractStrings(lines);
    }

}
