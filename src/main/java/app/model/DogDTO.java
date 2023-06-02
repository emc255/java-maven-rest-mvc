package app.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DogDTO {
    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private DogBreed dogBreed;

    @NotNull
    @NotBlank
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
