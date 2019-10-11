package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;

public interface TodoService {

    Todo update(Todo todo, long id);
}
