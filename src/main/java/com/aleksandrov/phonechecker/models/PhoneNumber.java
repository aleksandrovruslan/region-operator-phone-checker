package com.aleksandrov.phonechecker.models;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int prefix;
    private int number;
    private String region;
    private String operator;
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private Set<Post> posts = new TreeSet<>();

    public PhoneNumber() {
    }

    public PhoneNumber(int prefix, int number) {
        this.prefix = prefix;
        this.number = number;
    }

    public PhoneNumber(int prefix, int number, String region, String operator) {
        this.prefix = prefix;
        this.number = number;
        this.region = region;
        this.operator = operator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (prefix != that.prefix) return false;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        int result = prefix;
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", prefix=" + prefix +
                ", number=" + number +
                ", region='" + region + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
