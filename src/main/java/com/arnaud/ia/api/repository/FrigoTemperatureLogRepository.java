package com.arnaud.ia.api.repository;

import com.arnaud.ia.api.entity.FrigoTemperatureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FrigoTemperatureLogRepository extends JpaRepository<FrigoTemperatureLog,Long> {
    @Query("SELECT ftl FROM FrigoTemperatureLog ftl WHERE ftl.frigo.idFrigo = :frigoId AND ftl.dateMesure >= :since")
    List<FrigoTemperatureLog> findLogsForLast30Min(@Param("frigoId") Integer frigoId, @Param("since") LocalDateTime since);
}
