package com.uni.forum.api;

import com.uni.forum.domain.dtos.ReplyDto;
import com.uni.forum.services.ReplyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173" , "http://localhost:8080"})
public class ReplyRest {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRest.class);

  private final ReplyService replyService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto reply) {
    ReplyDto persist = replyService.persist(reply);
    return ResponseEntity.ok(persist);
  }
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReplyDto>> getAllReplies(
          @RequestParam(value = "page", required = false) Integer page,
          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    if(page == null && pageSize == null)
      return ResponseEntity.ok(replyService.getAllReplies(0, 5));
    else
      return ResponseEntity.ok(replyService.getAllReplies(page, pageSize));
  }
  @GetMapping(path = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReplyDto>> getReplyByTopic(
          @PathVariable Long topicId,
          @RequestParam(value = "page", required = false) Integer page,
          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    if(page == null && pageSize == null)
      return ResponseEntity.ok(replyService.getAllRepliesByTopic(topicId, 0, 5));
    else
      return ResponseEntity.ok(replyService.getAllRepliesByTopic(topicId, page, pageSize));
  }
  @PutMapping(path = "/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReplyDto> updateReply(
          @PathVariable Long replyId, @RequestBody ReplyDto reply) {
    return ResponseEntity.ok(replyService.updateUser(replyId, reply));
  }
}
