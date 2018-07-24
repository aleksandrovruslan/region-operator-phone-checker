package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class PhoneInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    @Pattern(regexp = "^[0-9]{3}$")
    private String prefix;
    @Pattern(regexp = "^[0-9]{7}$")
    private String startInterval;
    @Pattern(regexp = "^[0-9]{7}$")
    private String endInterval;
    @ManyToOne(optional = false, fetch = FetchType.EAGER
            , cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "operator_id")
    private PhoneOperator operator;
    @ManyToOne(optional = false, fetch = FetchType.EAGER
            , cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "region_id")
    private PhoneRegion region;

    public PhoneInterval() {
    }

    public PhoneInterval(@Pattern(regexp = "[0-9]{3}") String prefix
            , @Pattern(regexp = "[0-9]{7}") String startInterval
            , @Pattern(regexp = "[0-9]{7}") String endInterval
            , PhoneOperator operator, PhoneRegion region) {
        this.prefix = prefix;
        this.startInterval = startInterval;
        this.endInterval = endInterval;
        this.operator = operator;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getStartInterval() {
        return startInterval;
    }

    public void setStartInterval(String startInterval) {
        this.startInterval = startInterval;
    }

    public String getEndInterval() {
        return endInterval;
    }

    public void setEndInterval(String endInterval) {
        this.endInterval = endInterval;
    }

    public PhoneOperator getOperator() {
        return operator;
    }

    public void setOperator(PhoneOperator operator) {
        this.operator = operator;
    }

    public PhoneRegion getRegion() {
        return region;
    }

    public void setRegion(PhoneRegion region) {
        this.region = region;
    }

    //TODO think what to add to the comparison


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneInterval interval = (PhoneInterval) o;

        if (!prefix.equals(interval.prefix)) return false;
        if (!startInterval.equals(interval.startInterval)) return false;
        return endInterval.equals(interval.endInterval);
    }

    @Override
    public int hashCode() {
        int result = prefix.hashCode();
        result = 31 * result + startInterval.hashCode();
        result = 31 * result + endInterval.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PhoneInterval{" +
                "id=" + id +
                ", prefix='" + prefix + '\'' +
                ", startInterval='" + startInterval + '\'' +
                ", endInterval='" + endInterval + '\'' +
                ", operator=" + operator +
                ", region=" + region +
                '}';
    }
}
