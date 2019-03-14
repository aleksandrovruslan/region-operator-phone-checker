package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneOperator;

import java.util.Collection;
import java.util.List;

public interface PhoneOperatorService {

    List<PhoneOperator> getOperators();

    PhoneOperator getOperator(int id);

    PhoneOperator saveOperator(PhoneOperator operator);

    void saveOperators(Collection<PhoneOperator> operators);

    void deleteOperator(PhoneOperator operator);

}
