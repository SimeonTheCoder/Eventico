package com.eventico.repo;

import com.eventico.model.entity.RedeemableCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedeemableCodeRepository extends JpaRepository<RedeemableCode, Long> {
    RedeemableCode findByCode(String code);

}
