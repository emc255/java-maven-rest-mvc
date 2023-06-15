package app.model;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;

    private Integer version;

    @NotBlank
    @NotNull
    private String name;

    @Column
    private String email;


    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
