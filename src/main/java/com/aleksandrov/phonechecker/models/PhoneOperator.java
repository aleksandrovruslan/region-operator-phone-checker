package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PhoneOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PhoneInterval> intervals = new HashSet<>();

    public PhoneOperator() {
    }

    public PhoneOperator(String name) {
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

    public Set<PhoneInterval> getIntervals() {
        return intervals;
    }

    public void setIntervals(Set<PhoneInterval> intervals) {
        this.intervals = intervals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneOperator that = (PhoneOperator) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "PhoneOperator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
