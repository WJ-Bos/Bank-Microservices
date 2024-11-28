package com.wjbos.loans.repo;

import com.wjbos.loans.entity.Loans;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans,Integer> {

    @Transactional
    Optional<Loans> findByMobileNumber(String mobileNumber);
}
