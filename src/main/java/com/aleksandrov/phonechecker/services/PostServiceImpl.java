package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import com.aleksandrov.phonechecker.repositories.PhoneNumberDAO;
import com.aleksandrov.phonechecker.repositories.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

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
    public void addPost(Post post) {
        PhoneNumber number = numberDAO.findFirstByPrefixEqualsAndNumberEquals(
                post.getPhoneNumber().getPrefix(), post.getPhoneNumber().getNumber());
        if (number == null) {
            number = new PhoneNumber(post.getPhoneNumber().getPrefix()
                    , post.getPhoneNumber().getNumber());
            checkService.check(number);
        }

        post.setPhoneNumber(number);
        post.setDateTime(LocalDateTime.now());
        postDAO.save(post);
    }
}
