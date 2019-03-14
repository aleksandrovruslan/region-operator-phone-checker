package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.repositories.PhoneIntervalDAO;
import com.aleksandrov.phonechecker.repositories.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private PhoneIntervalDAO intervalDAO;
    @Autowired
    private PostDAO postDAO;

    @Override
    public List<PhoneNumber> check(String id) {
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher;
        List<PhoneNumber> numberList = new LinkedList<>();
        String[] numbers = id.split(",");
        for (int i = 0; i < numbers.length; i++) {
            matcher = pattern.matcher(numbers[i]);
            if (matcher.find()) {
                numberList.add(fillRegionAndOperator(numbers[i].substring(0, 3),
                        numbers[i].substring(3, 10)));
            } else {
                numberList.add(new PhoneNumber(numbers[i]));
            }
        }
        return numberList;
    }

    private PhoneNumber fillRegionAndOperator(String prefix, String number) {
        PhoneNumber phoneNumber = new PhoneNumber(prefix, number);
        PhoneInterval interval = intervalDAO
                .findTop1ByPrefixEqualsAndEndIntervalGreaterThanEqualAndStartIntervalLessThanEqual(
                        prefix, number, number);
        if (interval != null) {
            phoneNumber.setOperator(interval.getOperator().getName());
            phoneNumber.setRegion(interval.getRegion().getName());
            phoneNumber.setTimeZoneUTC(interval.getRegion().getTimeZoneUTC());
        }
        try {
            phoneNumber.setPosts(postDAO.findAllByPhoneNumberEquals(phoneNumber));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

}
