package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import com.aleksandrov.phonechecker.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PostController.POST_MAPPING)
public class PostController {

    public static final String POST_MAPPING = "/api/v1/post";
    public static final String ADD_POST = "/add/";
    @Autowired
    private PostService postService;

    @PostMapping(value = ADD_POST)
    public PhoneNumber addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

}
