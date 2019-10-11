package com.lambdaschool.todo.services;

import com.lambdaschool.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "todoService")
public class TodoServiceImpl {

    @Autowired
    private TodoRepository todorepos;

}
