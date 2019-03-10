package com.aleksandrov.phonechecker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LinkExtractor {
    @Autowired
    private ConfigProperties properties;

    public Set<String> extractLinks(@NotNull Stream<String> lines) {
        return lines.flatMap(line -> Arrays.stream(line.split(properties.getSplitDelimiter())))
                .filter(line -> properties.getDownloadFileNames().stream()
                        .anyMatch(mark -> line.matches(".*" + mark + ".*")
                                && line.matches(".*" + properties.getStartWrap() + ".*")
                                && line.matches(".*" + properties.getEndWrap() + ".*")))
                .map(line -> properties.getLinkPrefix() + line.substring(
                        line.indexOf(properties.getStartWrap()) + properties.getStartWrap().length()
                        , line.indexOf(properties.getEndWrap()))).collect(Collectors.toSet());
    }

}
