package com.uni.forum.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private long id;
    private String title;
    private String username;
    private Date created;
    private Date modified;
    private long views;
}
