package com.Practice.Employee.Management.Modal;

import java.util.List;

public class TodoResponse {
	
	private Todo todo;
	private List<Todo> todos;
	public Todo getTodo() {
		return todo;
	}
	public void setTodo(Todo todo) {
		this.todo = todo;
	}
	public List<Todo> getTodos() {
		return todos;
	}
	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
	@Override
	public String toString() {
		return "TodoResponse [todo=" + todo + ", todos=" + todos + "]";
	}
	
	

}
