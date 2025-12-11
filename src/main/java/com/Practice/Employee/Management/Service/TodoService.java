package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Todo;
import com.Practice.Employee.Management.Modal.TodoResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface TodoService {

	GenericResponse<TodoResponse> addTodo(Todo todo, String operation);

	GenericResponse<TodoResponse> getAllTodos(String search, String date, String operation);

	GenericResponse<TodoResponse> getTodoById(Long id, String operation);

	GenericResponse<TodoResponse> updateTodo(Long id, Todo todo, String operation);

	GenericResponse<Void> deleteTodo(Long id, String operation);

}
