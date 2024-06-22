package com.uni.forum.api;

import com.uni.forum.domain.dtos.TopicDto;
import com.uni.forum.services.TopicService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173", "http://localhost:8080"})
public class TopicRest {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRest.class);

  private final TopicService topicService;

  @PostMapping(
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topic) {
    TopicDto persist = topicService.persist(topic);
    return ResponseEntity.ok(persist);
  }

  @GetMapping(path = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TopicDto>> getReplyByTopic(
          @PathVariable String username,
          @RequestParam(value = "page", required = false) Integer page,
          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    return ResponseEntity.ok(topicService.getAllTopicsByUsername(
      username,
      page == null ? 0 : page,
      pageSize == null ? 5 : pageSize
    ));
  }

  // TODO: implement
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TopicDto> getTopic(@PathVariable("id") Long id) {
    return ResponseEntity.ok(topicService.getTopic(id)); // this throws exception when not found!
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TopicDto>> getAllTopics(
          @RequestParam(value = "page", required = false) Integer page,
          @RequestParam(value = "pageSize", required = false) Integer pageSize
  ) {
    if (page == null) {
      page = 0;
    }

    if (pageSize == null) {
      pageSize = 10; // Default return 10 results
    }
    return ResponseEntity.ok(topicService.getAllTopics(page, pageSize));
  }

  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TopicDto> updateUser(
          @PathVariable("id") Long id, @RequestBody TopicDto user) {
    return ResponseEntity.ok(topicService.updateTopic(id, user));
  }
}
