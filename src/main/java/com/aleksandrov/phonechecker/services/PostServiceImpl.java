package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import com.aleksandrov.phonechecker.repositories.PhoneNumberDAO;
import com.aleksandrov.phonechecker.repositories.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping()
public class PostServiceImpl implements PostService {

    @Autowired
    private PhoneNumberDAO numberDAO;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private CheckService checkService;

    @Override
    public PhoneNumber addPost(Post post) {
        PhoneNumber phoneNumber = numberDAO.findById(post.getPhoneNumber().getPrefix()
                + post.getPhoneNumber().getNumber());
        if (phoneNumber == null) {
            phoneNumber = new PhoneNumber(post.getPhoneNumber().getPrefix()
                    , post.getPhoneNumber().getNumber());
            checkService.check(phoneNumber.getPrefix() + phoneNumber.getNumber());
        }
        post.setPhoneNumber(phoneNumber);
        postDAO.save(post);
        phoneNumber.setPosts(postDAO.findAllByPhoneNumberEquals(phoneNumber));
        return phoneNumber;
    }

}
