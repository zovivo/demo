package com.example.demo.entity;

import com.example.demo.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "created_at")
    protected Timestamp createdAt;
    @Column(name = "updated_at")
    protected Timestamp updatedAt;
    @ColumnDefault(value="false")
    @Column(name = "deleted", columnDefinition = "boolean default false")
    protected boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = CommonUtils.getCurrentTime();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = CommonUtils.getCurrentTime();
    }


}
