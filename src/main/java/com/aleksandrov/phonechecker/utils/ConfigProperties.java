package com.aleksandrov.phonechecker.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "app")
public class ConfigProperties {

    private String downloadPage;
    private List<String> downloadFileNames;
    private String startWrap;
    private String endWrap;
    private String linkPrefix;
    private String splitDelimiter;

    public String getDownloadPage() {
        return downloadPage;
    }

    public void setDownloadPage(String downloadPage) {
        this.downloadPage = downloadPage;
    }

    public List<String> getDownloadFileNames() {
        return downloadFileNames;
    }

    public void setDownloadFileNames(List<String> downloadFileNames) {
        this.downloadFileNames = downloadFileNames;
    }

    public String getStartWrap() {
        return startWrap;
    }

    public void setStartWrap(String startWrap) {
        this.startWrap = startWrap;
    }

    public String getEndWrap() {
        return endWrap;
    }

    public void setEndWrap(String endWrap) {
        this.endWrap = endWrap;
    }

    public String getLinkPrefix() {
        return linkPrefix;
    }

    public void setLinkPrefix(String linkPrefix) {
        this.linkPrefix = linkPrefix;
    }

    public String getSplitDelimiter() {
        return splitDelimiter;
    }

    public void setSplitDelimiter(String splitDelimiter) {
        this.splitDelimiter = splitDelimiter;
    }

}
