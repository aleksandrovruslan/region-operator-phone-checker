package com.aleksandrov.phonechecker.models;

import javax.persistence.*;

@Entity
public class PhoneInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int prefix;
    private int startInterval;
    private int endInterval;
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

    public PhoneInterval(int prefix, int startInterval, int endInterval
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

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getStartInterval() {
        return startInterval;
    }

    public void setStartInterval(int startInterval) {
        this.startInterval = startInterval;
    }

    public int getEndInterval() {
        return endInterval;
    }

    public void setEndInterval(int endInterval) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneInterval that = (PhoneInterval) o;

        if (prefix != that.prefix) return false;
        if (startInterval != that.startInterval) return false;
        return endInterval == that.endInterval;
    }

    @Override
    public int hashCode() {
        int result = prefix;
        result = 31 * result + startInterval;
        result = 31 * result + endInterval;
        return result;
    }

    @Override
    public String toString() {
        return "PhoneInterval{" +
                "id=" + id +
                ", prefix=" + prefix +
                ", startInterval=" + startInterval +
                ", endInterval=" + endInterval +
                ", operator=" + operator +
                ", region=" + region +
                '}';
    }
}
