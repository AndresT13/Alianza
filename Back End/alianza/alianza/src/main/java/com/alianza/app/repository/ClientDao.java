package com.alianza.app.repository;

import com.alianza.app.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface ClientDao extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findBySharedKey(String sharedKey);
}
