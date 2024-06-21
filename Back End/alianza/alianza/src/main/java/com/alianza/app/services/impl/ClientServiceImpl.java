package com.alianza.app.services.impl;

import com.alianza.app.mappers.MapperObjectsClient;
import com.alianza.app.model.dto.ClientDto;
import com.alianza.app.model.entities.ClientEntity;
import com.alianza.app.repository.ClientDao;
import com.alianza.app.services.IClientService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientDao clientRepository;


    private final Faker faker = new Faker();

    @Autowired
    public ClientServiceImpl(ClientDao clientRepository){
        this.clientRepository = clientRepository;

    }
    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> findAll() {
        List<ClientEntity> clienttEntityList = clientRepository.findAll();
        List<ClientDto> clientDtoList = new ArrayList<>();
        for(ClientEntity entity : clienttEntityList){
            clientDtoList.add(MapperObjectsClient.clientEntityToClientDto(entity));
        }
        return clientDtoList;
    }

    @Override
    public ClientDto getSharedKey(String sharedKey) {
        return null;
    }


    public List<ClientEntity> getFakeClients(int count) {
        List<ClientEntity> clients = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ClientEntity client = new ClientEntity();
            client.setSharedKey(faker.bothify("??##"));
            client.setName(faker.company().name());
            client.setEmail(faker.internet().emailAddress());
            client.setPhone(faker.phoneNumber().phoneNumber());
            client.setStartDate(faker.date().past(1000, java.util.concurrent.TimeUnit.DAYS));
            clients.add(client);
        }
        return  clients;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        Optional<ClientEntity> entity = clientRepository.findBySharedKey(clientDto.getSharedKey());
        ClientEntity client = null;
        if(!entity.isPresent()){
            client = clientRepository.save(MapperObjectsClient.clientDtoToClientEntity(clientDto));
        }
        return MapperObjectsClient.clientEntityToClientDto(client);
    }

    @Override
    public Page<ClientEntity> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
}
