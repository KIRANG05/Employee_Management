package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.Todo;
import com.Practice.Employee.Management.Modal.TodoResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.TaskService;
import com.Practice.Employee.Management.Service.TodoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todo")
@SecurityRequirement(name = "BearerAuth")
public class TodoController {

	private TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/add")
	public ResponseEntity<GenericResponse<TodoResponse>> addTodo( @Valid @RequestBody Todo todo, HttpServletRequest request) {

		String operation = request.getRequestURI();

		GenericResponse<TodoResponse> response =  todoService.addTodo(todo, operation);

		if (response.getIsSuccess()){
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}	

	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/getAll")
	public ResponseEntity<GenericResponse<TodoResponse>> getAllTodos(
			@RequestParam(required = false) String search,
	        @RequestParam(required = false) String date,
			HttpServletRequest request) {
		String operation = request.getRequestURI();
		GenericResponse<TodoResponse> response = todoService.getAllTodos(search, date, operation);
		if (response.getIsSuccess()){
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/fetch/{id}")
	public ResponseEntity<GenericResponse<TodoResponse>> getTodo(@PathVariable Long id, HttpServletRequest request) {
		String operation = request.getRequestURI();
		GenericResponse<TodoResponse> response = todoService.getTodoById(id, operation);
		if (response.getIsSuccess()){
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/update/{id}")
	public ResponseEntity<GenericResponse<TodoResponse>> updateTodo(@PathVariable Long id,
			@RequestBody Todo todo,
			HttpServletRequest request) {
		String operation = request.getRequestURI();
		GenericResponse<TodoResponse> response = todoService.updateTodo(id, todo, operation);

		if (response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GenericResponse<Void>> deleteTodo(@PathVariable Long id, HttpServletRequest request) {
		String operation = request.getRequestURI();
		GenericResponse<Void> response = todoService.deleteTodo(id, operation);

		if (response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
	}

}
