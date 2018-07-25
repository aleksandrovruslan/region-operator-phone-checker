package com.aleksandrov.phonechecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;
    @NotNull
    private String comment;
    private LocalDateTime dateTime;
    @ManyToOne(optional = false, fetch = FetchType.EAGER
            , cascade = {CascadeType.MERGE, CascadeType.PERSIST
            , CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "phone_number_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PhoneNumber phoneNumber;
    private String userName = "Гость";

    public Post() {
        this.dateTime = LocalDateTime.now();
    }

    public Post(String comment, PhoneNumber phoneNumber) {
        this.dateTime = LocalDateTime.now();
        this.comment = comment;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return id == post.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", dateTime=" + dateTime +
                ", phoneNumber=" + phoneNumber +
                ", userName='" + userName + '\'' +
                '}';
    }
}
