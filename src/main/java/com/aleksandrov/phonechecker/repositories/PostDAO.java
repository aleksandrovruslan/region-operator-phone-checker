package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PostDAO extends JpaRepository<Post, Long> {
    Collection<Post> findAllByPhoneNumberEquals(PhoneNumber number);
}
