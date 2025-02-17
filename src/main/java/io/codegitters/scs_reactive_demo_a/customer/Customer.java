package io.codegitters.scs_reactive_demo_a.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
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
@Table("customers")
public class Customer { 
  
    @Id 
    private UUID id;
  
    @Column("full_name") 
    private String fullName;
  
    @LastModifiedDate 
    private LocalDateTime timestamp;
  
    @Version 
    private Long version;
}
