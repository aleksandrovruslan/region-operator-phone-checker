package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneOperatorDAO extends JpaRepository<PhoneOperator, Integer> {
}
