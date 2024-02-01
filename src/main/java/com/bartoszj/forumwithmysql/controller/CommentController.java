package com.bartoszj.forumwithmysql.controller;

import com.bartoszj.forumwithmysql.controller.responses.CustomResponseGenerator;
import com.bartoszj.forumwithmysql.model.ModelMapper;
import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.comments.CommentDtoIn;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import com.bartoszj.forumwithmysql.repository.CommentRepository;
import com.bartoszj.forumwithmysql.repository.ThreadRepository;
import com.bartoszj.forumwithmysql.repository.UserRepository;
import java.security.Principal;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/comments"})
public class CommentController {
    private CommentRepository commentRepository;
    private ThreadRepository threadRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public CommentController(CommentRepository commentRepository,
                             ThreadRepository threadRepository,
                             UserRepository userRepository,
                             ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"/username={username}"})
    public ResponseEntity<Object> getCommentsByUser(@PathVariable String username,
                                                    @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize) {
        Optional<User> userOptional = this.userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return CustomResponseGenerator
                    .generateResponseNoData("User not found", HttpStatus.BAD_REQUEST);
        }
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Comment> pageComment = commentRepository
                .findCommentsByUser(userOptional.get(), pageRequest);
        return CustomResponseGenerator
                .generateResponse("Comments of User: " + username,
                        HttpStatus.ACCEPTED,
                        pageComment.map(l -> modelMapper.commentToDtoOut(l)));
    }

    @GetMapping({"/thread={threadId}"}) //move to Thread controller
    public ResponseEntity<Object> getCommentsByThread(@PathVariable Long threadId,
                                                      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize) {
        Optional<Thread> threadOptional = this.threadRepository.findById(threadId);
        if (threadOptional.isEmpty()) {
            return CustomResponseGenerator
                    .generateResponseNoData("Could not find thread", HttpStatus.BAD_REQUEST);
        }
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Comment> pageComment = commentRepository
                .findCommentsByThread(threadOptional.get(), pageRequest);
        return CustomResponseGenerator
                .generateResponse("Comments of thread: " + threadOptional.get().getTitle() + " - by: " + threadOptional.get().getUser().getUsername(),
                        HttpStatus.ACCEPTED,
                        pageComment.map(l ->modelMapper.commentToDtoOut(l)));
    }

    @PostMapping({"/add_comment"})
    @ResponseBody
    public ResponseEntity<Object> createComment(Principal principal, @Valid @RequestBody CommentDtoIn commentDtoIn) {
        Optional<Thread> optionalThread = this.threadRepository.findById(commentDtoIn.getThreadId());
        if (optionalThread.isEmpty()) {
            return CustomResponseGenerator
                    .generateResponseNoData("Could not find thread", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByUsername(principal.getName()).get();
        Thread thread = optionalThread.get();
        Comment comment = new Comment(commentDtoIn.getContent(), user, thread);
        thread.addComment(comment);
        user.addComment(comment);
        this.threadRepository.save(thread);
        this.userRepository.save(user);
        this.commentRepository.save(comment);
        return CustomResponseGenerator
                .generateResponseNoData("Comment added", HttpStatus.CREATED);
    }
}