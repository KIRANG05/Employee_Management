package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Todo;
import com.Practice.Employee.Management.Modal.TodoResponse;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TodoRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.TodoService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class TodoServiceImpl implements TodoService {
	
	private TodoRepository todoRepository;
	private ResponseCodeRespository responseCode;
	public TodoServiceImpl(TodoRepository todoRepository, ResponseCodeRespository responseCode) {
		this.todoRepository = todoRepository;
		this.responseCode = responseCode;
	}
	


	@Override
	public GenericResponse<TodoResponse> addTodo(Todo todo, String operation) {
		
		GenericResponse<TodoResponse> response = new GenericResponse<>();
		
		try {
		
		Todo saved = todoRepository.save(todo);
		
		TodoResponse todoResponse = new TodoResponse();
		todoResponse.setTodo(saved);
		
		String msg = responseCode.getMessageByCode(ResponseCode.TODO_ADD_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage(msg);
		
		response.setData(todoResponse);
		} catch (Exception e) {
			String msg = responseCode.getMessageByCode(ResponseCode.TODO_ADD_FAILED, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}
		return response;
	}



	@Override
	public GenericResponse<TodoResponse> getAllTodos(String search, String dateStr, String operation) {
		
		  GenericResponse<TodoResponse> response = new GenericResponse<>();
	        try {
	        	List<Todo> todos;
	        	
	        	 boolean hasSearch = search != null && !search.isEmpty();
	        	    boolean hasDate = dateStr != null && !dateStr.isEmpty();
	        	    
	        	    LocalDateTime start = null;
	                LocalDateTime end = null;
	        	    
	        	    if (hasDate) {
	                    try {
	                        // IMPORTANT: Match dd-MM-yyyy coming from frontend
	                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	                        LocalDate date = LocalDate.parse(dateStr, formatter);

	                          start = date.atStartOfDay();
	                        end = date.atTime(23, 59, 59);

	                    } catch (Exception e) {
	                        throw new IllegalArgumentException("Invalid date format. Expected dd-MM-yyyy");
	                    }
	                }
	        	    
	        	  if (hasSearch && hasDate) {
//	                  LocalDate date = LocalDate.parse(dateStr);
//	                  LocalDateTime start = date.atStartOfDay();
//	                  LocalDateTime end = date.atTime(23, 59, 59);
	                  todos = todoRepository.findByTitleContainingIgnoreCaseAndCreatedAtBetween(search, start, end);
	              } else if (hasSearch) {
	                  todos = todoRepository.findByTitleContainingIgnoreCase(search);
	              } else if (hasDate) {
//	                  LocalDate date = LocalDate.parse(dateStr);
//	                  LocalDateTime start = date.atStartOfDay();
//	                  LocalDateTime end = date.atTime(23, 59, 59);
	                  todos = todoRepository.findByCreatedAtBetween(start, end);
	              } else {
	                  todos = todoRepository.findAll();
	              }
	            
	            TodoResponse todoResponse = new TodoResponse();
	            todoResponse.setTodos(todos);
	            
	            String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	            response.setIsSuccess(true);
	            response.setStatus("Success");
	            response.setMessage(msg);
	            response.setData(todoResponse);

	        } catch (Exception e) {
	        	String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(msg);
	            response.setData(null);
	        }
	        return response;
	    }



	@Override
	public GenericResponse<TodoResponse> getTodoById(Long id, String operation) {
		
		GenericResponse<TodoResponse> response = new GenericResponse<>();
        try {
            Todo todo = todoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Todo not found"));
            TodoResponse todoResponse = new TodoResponse();
            todoResponse.setTodo(todo);

            String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
            response.setIsSuccess(true);
            response.setStatus("Success");
            response.setMessage(msg);
            response.setData(todoResponse);

        } catch (Exception e) {
        	String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
            response.setIsSuccess(false);
            response.setStatus("Failed");
            response.setMessage(msg);
            response.setData(null);
        }
        return response;
	}



	@Override
	public GenericResponse<TodoResponse> updateTodo(Long id, Todo todo, String operation) {
		
		 GenericResponse<TodoResponse> response = new GenericResponse<>();
	        try {
	            Todo existing = todoRepository.findById(id)
	                    .orElseThrow(() -> new RuntimeException("Todo not found"));

	            // Only allow updating title and description or status as needed
	            if (todo.getTitle() != null) {
	            	existing.setTitle(todo.getTitle());
	            }
	            if (todo.getDescription() != null) {
	            	existing.setDescription(todo.getDescription());
	            }
	           

	            Todo saved = todoRepository.save(existing);

	            TodoResponse todoResponse = new TodoResponse();
	            todoResponse.setTodo(saved);

	            String msg = responseCode.getMessageByCode(ResponseCode.TODO_UPDATE_SUCCESS, operation);
	            response.setIsSuccess(true);
	            response.setStatus("Success");
	            response.setMessage(msg);
	            response.setData(todoResponse);

	        } catch (Exception e) {
	            String msg = responseCode.getMessageByCode(ResponseCode.TODO_UPDATE_FAILED, operation);
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(msg);
	            response.setData(null);
	        }
	        return response;
	}



	@Override
	public GenericResponse<Void> deleteTodo(Long id, String operation) {
		
		GenericResponse<Void> response = new GenericResponse<>();
        try {
            todoRepository.deleteById(id);
            String msg = responseCode.getMessageByCode(ResponseCode.TODO_DELETE_SUCCESS, operation);
            response.setIsSuccess(true);
            response.setStatus("Success");
            response.setMessage(msg);
            response.setData(null);
        } catch (Exception e) {
            String msg = responseCode.getMessageByCode(ResponseCode.TODO_DELETE_FAILED, operation);
            response.setIsSuccess(false);
            response.setStatus("Failed");
            response.setMessage(msg);
            response.setData(null);
        }
        return response;
	}
	

}
