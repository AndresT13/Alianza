package com.alianza.app.model.dto;

import com.alianza.app.model.entities.ClientEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
public class ClientDto implements Serializable {
    private Long id;
    private String sharedKey;
    private String name;
    private String phone;
    private String email;
    private Date startDate;
    private Date endDate;

    private static final long serialVersionUID = 5462223600L;

}
