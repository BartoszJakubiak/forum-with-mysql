package com.bartoszj.forumwithmysql.controller;

import com.bartoszj.forumwithmysql.controller.responses.CustomResponseGenerator;
import com.bartoszj.forumwithmysql.model.ModelMapper;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.threads.ThreadDtoIn;
import com.bartoszj.forumwithmysql.model.threads.ThreadDtoOut;
import com.bartoszj.forumwithmysql.model.users.User;
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
@RequestMapping({"/threads"})
public class ThreadController {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ThreadController(ThreadRepository threadRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Object> getAllThreads() {
        List<ThreadDtoOut> list = new ArrayList();
        this.threadRepository.findAll().forEach(l -> list.add(this.modelMapper.threadToDtoOut(l)));
        return CustomResponseGenerator
                .generateResponse("List of existing threads: ",
                        HttpStatus.ACCEPTED,
                        list);
    }

    @GetMapping({"/my_threads"})
    public ResponseEntity<Object> getUserThreads(Principal principal) {
        List<ThreadDtoOut> list = new ArrayList();
        threadRepository.findThreadByUser(userRepository.findByUsername(principal.getName()).get())
                .forEach(l -> list.add(this.modelMapper.threadToDtoOut(l)));
        return CustomResponseGenerator
                .generateResponse("List of my threads: ",
                        HttpStatus.ACCEPTED, list);
    }

    @GetMapping({"/username={username}"})
    public ResponseEntity<Object> getUserThreads(@PathVariable String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return CustomResponseGenerator
                    .generateResponseNoData("Could not find user",
                            (HttpStatus.BAD_REQUEST));
        }
        List<ThreadDtoOut> list = new ArrayList();
        threadRepository
                .findThreadByUser(optionalUser.get())
                .forEach(l -> list.add(this.modelMapper.threadToDtoOut(l)));
        return CustomResponseGenerator
                .generateResponse(username + "'s threads",
                        HttpStatus.ACCEPTED, list);
    }

    @GetMapping({"/thread_id={id}"})
    public ResponseEntity<Object> getThreadbyId(@PathVariable Long id) {
        Optional<Thread> thread = this.threadRepository.findById(id);
        if (thread.isEmpty()) {
            return CustomResponseGenerator
                    .generateResponseNoData("Could not find thread",
                            HttpStatus.BAD_REQUEST);
        }
        return CustomResponseGenerator
                .generateResponse("Found thread",
                        HttpStatus.ACCEPTED,
                        modelMapper.threadToDtoOut(thread.get()));
    }

    @PostMapping({"/create_thread"})
    public ResponseEntity<Object> createThread(@Valid @RequestBody ThreadDtoIn newThread, Principal principal) {
        Optional<Thread> thread = threadRepository.findThreadByTitle(newThread.getTitle());
        if (thread.isPresent()) {
            return CustomResponseGenerator
                    .generateResponseNoData("Thread with this name already exists",
                            HttpStatus.BAD_REQUEST);
        } else {
            User author = userRepository.findByUsername(principal.getName()).get();
            Thread createdThread = new Thread();
            createdThread.setTitle(newThread.getTitle());
            createdThread.setCommentList(new ArrayList());
            createdThread.setUser(author);
            author.addThread(createdThread);
            userRepository.save(author);
            threadRepository.save(createdThread);
            return CustomResponseGenerator
                    .generateResponseNoData("Added new thread",
                            HttpStatus.CREATED);
        }
    }
}
