package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDAO extends JpaRepository<Post, Long> {

    List<Post> findAllByPhoneNumberEquals(PhoneNumber number);

}
