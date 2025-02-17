package io.codegitters.scs_reactive_demo_a.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction {
    
    @Id
    private UUID id;
    private Status status;
    private String description;
    private BigDecimal amount;
    @LastModifiedDate
    private LocalDateTime timestamp;
    @Version
    private Long version;
    
    @Getter
    @AllArgsConstructor
    public enum Status {
        WAITED("WAITED"), APPROVED("APPROVED"), REJECTED("REJECTED");
        private final String value;
    }
    
}
