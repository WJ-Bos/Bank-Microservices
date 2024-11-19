package com.wjbos.loans2.repo;

import com.wjbos.loans2.entity.LoansEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<LoansEntity,Integer> {

    @Transactional
    Optional<LoansEntity> findByMobileNumber(String mobileNumber);
}
