package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.repositories.PhoneOperatorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PhoneOperatorServiceImpl implements PhoneOperatorService {

    @Autowired
    private PhoneOperatorDAO operatorDAO;

    @Override
    public List<PhoneOperator> getOperators() {
        return operatorDAO.findAll();
    }

    @Override
    public PhoneOperator getOperator(int id) {
        return operatorDAO.getOne(id);
    }

    @Override
    public PhoneOperator saveOperator(PhoneOperator operator) {
        return operatorDAO.save(operator);
    }

    @Override
    public void saveOperators(Collection<PhoneOperator> operators) {
        operatorDAO.saveAll(operators);
    }

    @Override
    public void deleteOperator(PhoneOperator operator) {
        operatorDAO.delete(operator);
    }

}
