package com.aleksandrov.phonechecker.utils;

import com.aleksandrov.phonechecker.RegionOperatorPhoneCheckerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RegionOperatorPhoneCheckerApplication.class)
@WebAppConfiguration
public class LinkExtractorTest {
    @InjectMocks
    private LinkExtractor extractor;
    @Mock
    private ConfigProperties properties;
    private String downloadPage = "<br><a href=\"/upload/gallery/346/" +
            "33346_6afd95cd41c52dfbc9a4accb63f6f6a17004e559.csv\">" +
            "CSV-DEF-9</a></p></tr>";
    private Set<String> downloadFileNames = Stream.of("CSV-DEF-9").collect(Collectors.toSet());
    private String splitDelimiter = "<a ";
    private String startWrap = "href=\"";
    private String endWrap = "\">";
    private String linkPrefix = "https://rossvyaz.ru";
    private Set<String> downloadLinks = Stream.of("https://rossvyaz.ru/upload/" +
            "gallery/346/33346_6afd95cd41c52dfbc9a4accb63f6f6a17004e559.csv")
            .collect(Collectors.toSet());
    private Set<String> expectedSet;

    @Before
    public void before() {
        when(properties.getDownloadFileNames()).thenReturn(downloadFileNames);
        when(properties.getStartWrap()).thenReturn(startWrap);
        when(properties.getEndWrap()).thenReturn(endWrap);
        when(properties.getSplitDelimiter()).thenReturn(splitDelimiter);
        when(properties.getLinkPrefix()).thenReturn(linkPrefix);
    }

    @Test
    public void with_valid_argument_set_links() {
        expectedSet = extractor.extractLinks(Stream.of(downloadPage));
        assertTrue(expectedSet.equals(downloadLinks));
    }

    @Test(expected = NullPointerException.class)
    public void with_null_argument_throw() {
        extractor.extractLinks(null);
    }

    @Test
    public void with_empty_argument_empty_set() {
        expectedSet = extractor.extractLinks(Stream.empty());
        assertEquals(expectedSet.size(), 0);
    }

    @Test
    public void without_links_argument_empty_set() {
        expectedSet = extractor.extractLinks(Stream.of("sjahfskajf", "sajhdjas", "CSV-ABC-3"));
        assertEquals(expectedSet.size(), 0);
    }
}