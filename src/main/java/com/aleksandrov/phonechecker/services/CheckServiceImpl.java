package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.models.Post;
import com.aleksandrov.phonechecker.repositories.PhoneIntervalDAO;
import com.aleksandrov.phonechecker.repositories.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private PhoneIntervalDAO intervalDAO;
    @Autowired
    private PostDAO postDAO;

    @Override
    public PhoneNumber check(PhoneNumber number) {
        return fillRegionAndOperator(number);
    }

    @Override
    public List<PhoneNumber> checkAll(List<PhoneNumber> numbers) {
        numbers.forEach(number -> check(number));
        return numbers;
    }

    private PhoneNumber fillRegionAndOperator(PhoneNumber number) {
        PhoneInterval interval = intervalDAO
                .findTop1ByPrefixEqualsAndEndIntervalGreaterThanEqualAndStartIntervalLessThanEqual(
                        number.getPrefix(), number.getNumber(), number.getNumber());
        if (interval == null) {
            number.setOperator("This number is not in service.");
            number.setRegion("Region not found.");
        } else {
            number.setOperator(interval.getOperator().getName());
            number.setRegion(interval.getRegion().getName());
            try {
                number.setId(number.getPrefix() + number.getNumber());
                Set<Post> posts = new HashSet<>(postDAO.findAllByPhoneNumberEquals(number));
                number.setPosts(posts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return number;
    }
}
