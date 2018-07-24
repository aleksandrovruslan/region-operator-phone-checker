package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneNumber;
import com.aleksandrov.phonechecker.repositories.PhoneIntervalDAO;
import com.aleksandrov.phonechecker.repositories.PostDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckServiceImpl implements CheckService {
    private static final Logger log = LoggerFactory.getLogger("CheckServiceImpl");
    @Autowired
    private PhoneIntervalDAO intervalDAO;
    @Autowired
    private PostDAO postDAO;
    private Pattern pattern = Pattern.compile("^[0-9]{10}$");
    private Matcher matcher;


    @Override
    public PhoneNumber check(String id) {
        matcher = pattern.matcher(id);
        if (!matcher.find()) {
           throw new IllegalStateException("Некорректный номер.");
        }
        return fillRegionAndOperator(id.substring(0, 3), id.substring(3, 10));
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
