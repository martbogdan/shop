package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.OrderDeliveryCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryCompanyRepository extends JpaRepository<OrderDeliveryCompany, Long> {
}
