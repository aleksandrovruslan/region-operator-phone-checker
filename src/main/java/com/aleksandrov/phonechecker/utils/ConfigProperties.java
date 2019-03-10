package com.aleksandrov.phonechecker.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "app")
public class ConfigProperties {
    private List<String> downloadUrl;
    private String downloadPage;
    private Set<String> downloadFileNames;
    private String startWrap;
    private String endWrap;
    private String linkPrefix;
    private String splitDelimiter;

    public List<String> getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(List<String> downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadPage() {
        return downloadPage;
    }

    public void setDownloadPage(String downloadPage) {
        this.downloadPage = downloadPage;
    }

    public Set<String> getDownloadFileNames() {
        return downloadFileNames;
    }

    public void setDownloadFileNames(Set<String> downloadFileNames) {
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
