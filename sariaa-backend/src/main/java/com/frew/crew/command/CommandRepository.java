package com.frew.crew.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommandRepository extends JpaRepository<Command, UUID> {

  @Query("SELECT c FROM Command c WHERE c.status IN :statuses AND c.user.id = :userId")
  List<Command> findByStatusesAndUser(@Param("statuses") List<String> statuses, @Param("userId") UUID userId);

}
