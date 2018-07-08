package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneIntervalDAO extends JpaRepository<PhoneInterval, Integer> {
    PhoneInterval findTop1ByPrefixEqualsAndEndIntervalGreaterThanEqualAndStartIntervalLessThanEqual(String Prefix, String End, String Start);

    @Query(nativeQuery = true, value = "SELECT PHONE_INTERVAL.ID, PHONE_INTERVAL.END_INTERVAL, PHONE_INTERVAL.PREFIX, PHONE_INTERVAL.START_INTERVAL, PHONE_INTERVAL.OPERATOR_ID, PHONE_INTERVAL.REGION_ID FROM PHONE_INTERVAL " +
            "INNER JOIN PHONE_OPERATOR PO ON PHONE_INTERVAL.OPERATOR_ID = PO.ID " +
            "INNER JOIN PHONE_REGION PR ON PHONE_INTERVAL.REGION_ID = PR.ID "+
            "WHERE PHONE_INTERVAL.PREFIX = ? AND PHONE_INTERVAL.START_INTERVAL <= ? AND PHONE_INTERVAL.END_INTERVAL >= ? LIMIT 1")
    PhoneInterval findInterval(String prefix, String number, String number2);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM PHONE_INTERVAL")
    void deleteAll();
}
