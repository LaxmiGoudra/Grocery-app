package com.ls.grocery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ls.grocery_app.entity.Bill;

public interface BillRepository  extends JpaRepository<Bill, Long> {

}
