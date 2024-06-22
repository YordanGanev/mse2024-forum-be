package com.uni.forum.domain.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TopicEntity extends BaseEntity {

  @Column(unique = true)
  private String title;

  private String username;

  @Column
  @OneToMany(mappedBy = "topic")
  private Set<ReplyEntity> replies;

  @JoinColumn
  @ManyToOne
  private UserEntity user;

  private long views;
}
