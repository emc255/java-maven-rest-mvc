package app.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    private String breed;

    private String sex;

    private String color;

    @NotNull
    @NotBlank
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
