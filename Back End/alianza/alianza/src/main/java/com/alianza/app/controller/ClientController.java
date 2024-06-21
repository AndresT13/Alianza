package com.alianza.app.controller;

import com.alianza.app.exception.BadRequestException;
import com.alianza.app.exception.ResourceNotFoundException;
import com.alianza.app.model.dto.ClientDto;
import com.alianza.app.model.entities.ClientEntity;
import com.alianza.app.payload.MessageResponse;
import com.alianza.app.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api")
public class ClientController {

    public static IClientService clientService;

    @Autowired

    public ClientController(IClientService clientService) {
        this.clientService = clientService;

    }

    @GetMapping("/clients")
    public ResponseEntity<?> getClients()   {
        List<ClientDto> getList = clientService.findAll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("clientes");
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);

    }

    @GetMapping("/fake")
    public List<ClientEntity> getFakeClients() {
        return (List<ClientEntity>) clientService.getFakeClients(10);
    }

    @GetMapping(path="/sharedKey/{sharedKey}")
    public ResponseEntity<?> getNumberDocument(@PathVariable String sharedKey) {
        List<ClientDto> getList = clientService.findAll();
        ClientDto cliente = clientService.getSharedKey(sharedKey);

        if (cliente == null) {
            throw new ResourceNotFoundException("cliente", "número de documento: ", sharedKey);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(ClientDto.builder()
                                .sharedKey(cliente.getSharedKey())
                                .name(cliente.getName())
                                .email(cliente.getEmail())
                                .phone(cliente.getPhone())
                                .startDate(cliente.getStartDate())
                                .endDate(cliente.getEndDate())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("/clients/page/{page}")
    public Page<ClientEntity> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return clientService.findAll(pageable);
    }

    @RequestMapping(path = "/client/create" , method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createClient(@RequestBody ClientDto clientDto) {

        ClientDto clientSave = null;
        try {
            clientSave = clientService.save(clientDto);
            log.info("Se creó correctamente el cliente");
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado correctamente")
                    .object(ClientDto.builder()
                            .sharedKey(clientSave.getSharedKey())
                            .name(clientSave.getName())
                            .email(clientSave.getEmail())
                            .phone(clientSave.getPhone())
                            .startDate(clientSave.getStartDate())
                            .endDate(clientSave.getEndDate())
                            .build())
                    .build()
                    , HttpStatus.CREATED);

        } catch (DataAccessException exDt) {
            log.warn("Se creó correctamente el cliente");
            throw new BadRequestException(exDt.getMessage());
        }
    }


}
