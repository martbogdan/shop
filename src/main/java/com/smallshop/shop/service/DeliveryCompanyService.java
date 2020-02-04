package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.OrderDeliveryCompany;
import com.smallshop.shop.dao.repository.DeliveryCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryCompanyService {
    private DeliveryCompanyRepository deliveryCompanyRepository;

    @Autowired
    public DeliveryCompanyService(DeliveryCompanyRepository deliveryCompanyRepository) {
        this.deliveryCompanyRepository = deliveryCompanyRepository;
    }

    public List<OrderDeliveryCompany> getAllCompanies () {
        return deliveryCompanyRepository.findAll();
    }
}
