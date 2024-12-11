package com.arnaud.ia.api.repository;

import com.arnaud.ia.api.entity.Frigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrigoRepository extends JpaRepository<Frigo, Integer> {

}
