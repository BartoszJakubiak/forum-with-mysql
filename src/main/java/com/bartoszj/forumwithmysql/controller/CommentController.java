package com.bartoszj.forumwithmysql.controller;

import com.bartoszj.forumwithmysql.model.ModelMapper;
import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.comments.CommentDtoIn;
import com.bartoszj.forumwithmysql.model.comments.CommentDtoOut;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import com.bartoszj.forumwithmysql.repository.CommentRepository;
import com.bartoszj.forumwithmysql.repository.ThreadRepository;
import com.bartoszj.forumwithmysql.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<CommentDtoOut>> getCommentsByUser(@PathVariable String username) {

        Optional<User> userOptional = this.userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        List<CommentDtoOut> list = new ArrayList();
        commentRepository.findCommentsByUser(userOptional.get())
                .forEach((l) -> list.add(this.modelMapper.commentToDtoOut(l)));
        return ResponseEntity.ok(list);

    }

    @GetMapping({"/thread={threadId}"})
    public ResponseEntity<List<CommentDtoOut>> getCommentsByThread(@PathVariable Long threadId) {
        Optional<Thread> threadOptional = this.threadRepository.findById(threadId);
        if (threadOptional.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        List<CommentDtoOut> list = new ArrayList();
        commentRepository.findCommentsByThread(threadOptional.get())
                .forEach(l -> list.add(modelMapper.commentToDtoOut(l)));
        return ResponseEntity.ok(list);

    }

    @PostMapping({"/add_comment"})
    public String createComment(Principal principal, @Valid @RequestBody CommentDtoIn commentDtoIn) {
        Optional<Thread> optionalThread = this.threadRepository.findById(commentDtoIn.getThreadId());
        if (optionalThread.isEmpty()) {
            return "Could not find thread";
        }
        User user = (User)this.userRepository.findByUsername(principal.getName()).get();
        Thread thread = (Thread)optionalThread.get();
        Comment comment = new Comment(commentDtoIn.getContent(), user, thread);
        thread.addComment(comment);
        user.addComment(comment);
        this.threadRepository.save(thread);
        this.userRepository.save(user);
        this.commentRepository.save(comment);
        return "Comment added";
    }
}