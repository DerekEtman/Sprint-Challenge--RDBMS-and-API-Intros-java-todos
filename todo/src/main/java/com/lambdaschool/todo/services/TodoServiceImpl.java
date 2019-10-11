package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todorepos;


    public Todo update(Todo todo, long id) {
        Todo newTodo = todorepos.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo id " + id + " not " +
                                                                                                    "found!"));
        if(todo.getDescription()!=null)
            newTodo.setDescription(todo.getDescription());
        if(todo.getDatestarted()!=null)
            newTodo.setDatestarted(todo.getDatestarted());
        if(todo.getUser()!=null)
            newTodo.setUser(todo.getUser());

        newTodo.setCompleted(todo.isCompleted());

        return todorepos.save(newTodo);
    }
}
