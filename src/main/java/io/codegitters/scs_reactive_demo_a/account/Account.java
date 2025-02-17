package io.codegitters.scs_reactive_demo_a.account;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("accounts")
public class Account {
    
    @Id
    private UUID id;
    
    @Column("account_number")
    private String number;
    
    @LastModifiedDate
    private LocalDateTime timestamp;
    
    @Version
    private Long version;
}
