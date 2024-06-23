package com.uni.forum.services;

import com.uni.forum.domain.coverters.ReplyConverter;
import com.uni.forum.domain.dtos.ReplyDto;
import com.uni.forum.domain.entities.ReplyEntity;
import com.uni.forum.domain.entities.TopicEntity;
import com.uni.forum.domain.entities.UserEntity;
import com.uni.forum.exceptions.NonExistingEntityException;
import com.uni.forum.repositories.ReplyPagingRepository;
import com.uni.forum.repositories.ReplyRepository;
import com.uni.forum.repositories.TopicRepository;
import com.uni.forum.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    // TODO: Stanislav will review and fix (and break everything)

    private final ReplyRepository replyRepository;
    private final ReplyPagingRepository pagingRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final ReplyConverter converter;

    public ReplyDto persist(ReplyDto dto) {
        TopicEntity topicEntity = getTopicOrThrowException(dto.getTopicId());
        UserEntity userEntity = UserService.getUserOrThrowException(dto.getUsername(), userRepository);

        ReplyEntity entity = converter.toEntity(dto);
        entity.setTopic(topicEntity);
        entity.setUser(userEntity);
        ReplyEntity savedEntity = replyRepository.save(entity);
        return converter.toDto(savedEntity);
    }

    public List<ReplyDto> getAllRepliesByTopic(Long id, Integer page, Integer pageSize) {
        TopicEntity topic = getTopicOrThrowException(id);
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("created").ascending());
        List<ReplyEntity> allByTopic = pagingRepository.findAllByTopic(topic, pageRequest);
        return allByTopic.stream().map(converter::toDto).collect(Collectors.toList());
    }

    public List<ReplyDto> getAllReplies(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("created").ascending());
        Page<ReplyEntity> all = pagingRepository.findAll(pageRequest);
        return all.getContent().stream().map(converter::toDto).collect(Collectors.toList());
    }

    private TopicEntity getTopicOrThrowException(Long id) {
        Optional<TopicEntity> topic = topicRepository.findById(id);
        if (topic.isEmpty()){
            throw new NonExistingEntityException();
        }
        return topic.get();
    }

    public ReplyDto updateUser(long replyId, ReplyDto reply) {
        Optional<ReplyEntity> byiD = replyRepository.findById(replyId);
        if (byiD.isEmpty()) {
            throw new IllegalArgumentException(  "Reply not found: " + replyId);
        }
        ReplyEntity replyEntity = byiD.get();
        ReplyEntity updatedEntity = converter.toEntity(reply);
        updatedEntity.setId(replyEntity.getId());
        updatedEntity.setCreated(replyEntity.getCreated());
        ReplyEntity save = replyRepository.save(updatedEntity);
        return converter.toDto(save);
    }
}
