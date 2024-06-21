package com.alianza.app.mappers;

import com.alianza.app.model.dto.ClientDto;
import com.alianza.app.model.entities.ClientEntity;

import java.util.Optional;

public interface MapperObjectsClient {

    public static ClientDto clientEntityToClientDto(ClientEntity clientEntity){
        if (clientEntity == null) {
            return null;
        }
        return ClientDto.builder()
                .sharedKey(clientEntity.getSharedKey())
                .name(clientEntity.getName())
                .email(clientEntity.getEmail())
                .phone(clientEntity.getPhone())
                .startDate(clientEntity.getStartDate())
                .build();
    }

    public static ClientEntity clientDtoToClientEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        return ClientEntity.builder()
                .sharedKey(clientDto.getSharedKey())
                .name(clientDto.getName())
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .startDate(clientDto.getStartDate())
                .build();
    }
}
