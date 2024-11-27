package com.wjbos.cards.repository;

import com.wjbos.cards.entity.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<CardsEntity,Long> {
}
