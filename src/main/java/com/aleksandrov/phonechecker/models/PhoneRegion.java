package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PhoneRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int timeZoneUTC;
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PhoneInterval> phoneIntervals = new HashSet<>();

    public PhoneRegion() {
    }

    public PhoneRegion(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeZoneUTC() {
        return timeZoneUTC;
    }

    public void setTimeZoneUTC(int timeZoneUTC) {
        this.timeZoneUTC = timeZoneUTC;
    }

    public Set<PhoneInterval> getPhoneIntervals() {
        return phoneIntervals;
    }

    public void setPhoneIntervals(Set<PhoneInterval> phoneIntervals) {
        this.phoneIntervals = phoneIntervals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneRegion that = (PhoneRegion) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "PhoneRegion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeZoneUTC=" + timeZoneUTC +
                '}';
    }
}
