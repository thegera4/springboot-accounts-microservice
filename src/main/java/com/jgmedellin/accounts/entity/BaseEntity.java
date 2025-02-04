package com.jgmedellin.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@MappedSuperclass // Indicates that the class is an entity and that its attributes should be mapped to the database
@EntityListeners(AuditingEntityListener.class) // Allows the entity to be audited so that the fields annotated with @CreatedBy, @CreatedDate, @LastModifiedBy, and @LastModifiedDate are automatically populated
@Getter @Setter @ToString
public class BaseEntity {

  @CreatedDate // Automatically populate the field with the current date and time when the entity is persisted
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @CreatedBy // Automatically populate the field with the current user when the entity is persisted (requires logic, see audit package)
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedDate // Automatically populate the field with the current date and time when the entity is updated
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @LastModifiedBy // Automatically populate the field with the current user when the entity is updated
  @Column(insertable = false)
  private String updatedBy;

}