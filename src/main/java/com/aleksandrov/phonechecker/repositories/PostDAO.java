package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDAO extends JpaRepository<Post, Long> {
}
