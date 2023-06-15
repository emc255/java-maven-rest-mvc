package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dog {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String breed;

    @Size(max = 50)
    @Column(length = 50)
    private String sex;

    @Size(max = 50)
    @Column(length = 50)
    private String color;

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "dog")
    private Set<DogOrderLine> dogOrderLines;
}
