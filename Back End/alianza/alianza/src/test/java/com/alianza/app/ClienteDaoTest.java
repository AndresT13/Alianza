package com.alianza.app;

import com.alianza.app.model.entities.ClientEntity;
import com.alianza.app.repository.ClientDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClienteDaoTest {

    @Mock
    private ClientDao clientDao;

    @Test
    public void testFindBySharedKey() {
        // Datos de prueba
        String sharedKey = "testSharedKey";
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setSharedKey(sharedKey);

        // Configurar el comportamiento del mock
        when(clientDao.findBySharedKey(sharedKey)).thenReturn(Optional.of(clientEntity));

        // Llamar al método que se está probando
        Optional<ClientEntity> result = clientDao.findBySharedKey(sharedKey);

        // Verificar el resultado
        assertThat(result).isPresent();
        assertThat(result.get().getSharedKey()).isEqualTo(sharedKey);
    }


}
