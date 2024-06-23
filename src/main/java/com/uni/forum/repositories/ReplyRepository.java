package com.uni.forum.repositories;

import com.uni.forum.domain.entities.ReplyEntity;
import com.uni.forum.domain.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// TODO: add paging support (PagingAndSortingRepository)
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findAllByTopic(TopicEntity topic);

}
