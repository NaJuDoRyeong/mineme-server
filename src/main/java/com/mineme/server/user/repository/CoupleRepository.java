package com.mineme.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mineme.server.entity.Couple;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {

}
