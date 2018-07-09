package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class PhoneNumber {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    @NotNull
    @Pattern(regexp = "[0-9]{3}")
    private String prefix;
    @NotNull
    @Pattern(regexp = "[0-9]{7}")
    private String number;
    transient private String region;
    transient private String operator;
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private Set<Post> posts = new TreeSet<>();

    public PhoneNumber() {
    }

    public PhoneNumber(@Pattern(regexp = "[0-9]{3}") String prefix
            , @Pattern(regexp = "[0-9]{7}") String number) {
        this.id = prefix + number;
        this.prefix = prefix;
        this.number = number;
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber number1 = (PhoneNumber) o;

        if (!prefix.equals(number1.prefix)) return false;
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
                "id=" + id +
                ", prefix='" + prefix + '\'' +
                ", number='" + number + '\'' +
                ", region='" + region + '\'' +
                ", operator='" + operator + '\'' +
                ", posts=" + posts +
                '}';
    }
}
