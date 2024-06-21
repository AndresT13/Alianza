package com.alianza.app.services;

import com.alianza.app.model.dto.ClientDto;
import com.alianza.app.model.entities.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClientService {

    List<ClientDto> findAll();

    List<ClientEntity> getFakeClients(int count);
    ClientDto findBySharedKey(String sharedKey);
    ClientDto save(ClientDto clientDto);

    public Page<ClientEntity> findAll(Pageable pageable);

}
