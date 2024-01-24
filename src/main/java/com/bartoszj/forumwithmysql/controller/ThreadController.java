package com.bartoszj.forumwithmysql.controller;

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
    public List<ThreadDtoOut> getAllThreads() {
        List<ThreadDtoOut> list = new ArrayList();
        this.threadRepository.findAll().forEach((l) -> {
            list.add(this.modelMapper.threadToDtoOut(l));
        });
        return list;
    }

    @GetMapping({"/my_threads"})
    public List<ThreadDtoOut> getUserThreads(Principal principal) {
        List<ThreadDtoOut> list = new ArrayList();
        this.threadRepository.findThreadByUser((User)this.userRepository.findByUsername(principal.getName()).get()).forEach((l) -> {
            list.add(this.modelMapper.threadToDtoOut(l));
        });
        return list;
    }

    @GetMapping({"{id}"})
    public ResponseEntity<ThreadDtoOut> getThread(@PathVariable Long id) {
        Optional<Thread> thread = this.threadRepository.findById(id);
        return thread.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : ResponseEntity.ok(this.modelMapper.threadToDtoOut((Thread)thread.get()));
    }

    @PostMapping({"/create_thread"})
    public String createThread(@RequestBody ThreadDtoIn newThread, Principal principal) {
        Optional<Thread> thread = this.threadRepository.findThreadByTitle(newThread.getTitle());
        if (thread.isPresent()) {
            return "Thread with this title already exists: " + newThread.getTitle();
        } else {
            User author = userRepository.findByUsername(principal.getName()).get();
            Thread createdThread = new Thread();
            createdThread.setTitle(newThread.getTitle());
            createdThread.setCommentList(new ArrayList());
            createdThread.setUser(author);
            author.addThread(createdThread);
            userRepository.save(author);
            threadRepository.save(createdThread);
            return "Added new thread!";
        }
    }
}
