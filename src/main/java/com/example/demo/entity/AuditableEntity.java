package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
    @Column(nullable = false , updatable = false)
    @CreatedDate
    private LocalDate createdDate;
    @Column(nullable = false , updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDate updatedDate;
    @LastModifiedBy
    private String updatedBy;
}
