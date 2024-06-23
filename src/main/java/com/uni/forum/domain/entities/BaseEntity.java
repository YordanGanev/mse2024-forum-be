package com.uni.forum.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {

  @Id @GeneratedValue private long id;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date created;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date modified;
}
