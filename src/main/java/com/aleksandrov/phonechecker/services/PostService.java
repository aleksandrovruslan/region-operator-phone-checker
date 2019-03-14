package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;

public interface PostService {

    PhoneNumber addPost(Post post);

}
