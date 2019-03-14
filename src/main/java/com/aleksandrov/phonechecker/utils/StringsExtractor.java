package com.aleksandrov.phonechecker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
public class StringsExtractor {

    @Autowired
    private ConfigProperties properties;

    public Queue<String> extractStrings(@NotNull Collection<String> lines) {
        return lines.stream().flatMap(line -> Arrays.stream(line.split(
                properties.getSplitDelimiter()))).filter(line -> properties.
                getDownloadFileNames().stream().anyMatch(mark ->
                line.matches(".*" + mark + ".*")
                        && line.matches(".*" + properties.getStartWrap() + ".*")
                        && line.matches(".*" + properties.getEndWrap() + ".*"))).
                map(line -> properties.getLinkPrefix() + line.substring(line.
                        indexOf(properties.getStartWrap()) + properties.getStartWrap().length(),
                        line.indexOf(properties.getEndWrap()))).
                collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

}
