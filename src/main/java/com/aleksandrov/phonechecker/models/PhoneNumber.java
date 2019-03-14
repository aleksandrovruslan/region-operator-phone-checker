package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class PhoneNumber {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String prefix;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Pattern(regexp = "^[0-9]{7}$")
    private String number;
    private transient String fullNumber;
    private transient String region;
    private transient String operator;
    private transient LocalDateTime serverTime = LocalDateTime.now();
    private transient int timeZoneUTC = 3;
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private List<Post> posts = new LinkedList<>();

    public PhoneNumber() {
    }

    public PhoneNumber(@Pattern(regexp = "^[0-9]{3}$") String prefix,
                       @Pattern(regexp = "^[0-9]{7}$") String number) {
        this.id = prefix + number;
        this.prefix = prefix;
        this.number = number;
        this.fullNumber = prefix + number;
    }

    public PhoneNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LocalDateTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public int getTimeZoneUTC() {
        return timeZoneUTC;
    }

    public void setTimeZoneUTC(int timeZoneUTC) {
        this.timeZoneUTC = timeZoneUTC;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber number1 = (PhoneNumber) o;
        if (!prefix.equals(number1.prefix)) {
            return false;
        }
        return number.equals(number1.number);
    }

    @Override
    public int hashCode() {
        int result = prefix.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", number='" + number + '\'' +
                ", fullNumber='" + fullNumber + '\'' +
                ", region='" + region + '\'' +
                ", operator='" + operator + '\'' +
                ", serverTime=" + serverTime +
                ", timeZoneUTC=" + timeZoneUTC +
                ", posts=" + posts +
                '}';
    }

}
