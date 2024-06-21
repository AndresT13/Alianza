package com.alianza.app;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.alianza.app.model.dto.ClientDto;
import com.alianza.app.model.entities.ClientEntity;
import com.alianza.app.repository.ClientDao;
import com.alianza.app.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientDao clientDao;

    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    public void testFindAll() {
        // Datos de prueba
        ClientEntity clientEntity1 = new ClientEntity();
        clientEntity1.setSharedKey("JPEREZ");
        clientEntity1.setEmail("jperez@mail.co");

        ClientEntity clientEntity2 = new ClientEntity();
        clientEntity2.setSharedKey("PLIMA");
        clientEntity2.setEmail("pablolima@mail.co");

        List<ClientEntity> clientEntityList = Arrays.asList(clientEntity1, clientEntity2);

        // Configurar el comportamiento del mock
        when(clientDao.findAll()).thenReturn(clientEntityList);

        // Llamar al método que se está probando
        List<ClientDto> result = clientService.findAll();

        // Verificar el resultado
        assertThat(result).isNotNull(); // Asegurarse de que el resultado no sea null
        assertThat(result).hasSize(2); // Verificar el tamaño de la lista
        assertThat(result.get(0).getSharedKey()).isEqualTo("JPEREZ"); // Verificar los valores de los DTO
        assertThat(result.get(0).getEmail()).isEqualTo("jperez@mail.co");
        assertThat(result.get(1).getSharedKey()).isEqualTo("PLIMA");
        assertThat(result.get(1).getEmail()).isEqualTo("pablolima@mail.co");
    }

    @Test
    public void testFindBySharedKey() {
        // Datos de prueba
        String sharedKey = "testSharedKey";
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setSharedKey("testSharedKey");
        clientEntity.setName("Test Client");
        clientEntity.setSharedKey(sharedKey);

        // Configurar el comportamiento del mock
        when(clientDao.findBySharedKey(sharedKey)).thenReturn(Optional.of(clientEntity));

        // Llamar al método que se está probando
        ClientDto result = clientService.findBySharedKey(sharedKey);

        // Verificar el resultado
        assertThat(result).isNotNull(); // Asegurarse de que el resultado no sea nulo
        assertThat(result.getSharedKey()).isEqualTo("testSharedKey"); // Verificar el ID esperado
        assertThat(result.getName()).isEqualTo("Test Client"); // Verificar el nombre esperado
    }

    @Test
    public void testFindBySharedKey_NotFound() {
        // Datos de prueba
        String sharedKey = "nonExistentSharedKey";

        // Configurar el comportamiento del mock
        when(clientDao.findBySharedKey(sharedKey)).thenReturn(Optional.empty());

        // Llamar al método que se está probando
        ClientDto result = clientService.findBySharedKey(sharedKey);

        // Verificar el resultado
        assertThat(result).isNull(); // Esperamos que sea null cuando no se encuentra ninguna entidad
    }
}
