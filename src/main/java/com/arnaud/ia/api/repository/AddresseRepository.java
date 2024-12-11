package com.arnaud.ia.api.repository;

import com.arnaud.ia.api.entity.Addresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresseRepository extends JpaRepository<Addresse, Integer> {

}
