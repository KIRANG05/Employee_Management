package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
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
		todoResponse.setTodo(todo);
		
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
	public GenericResponse<TodoResponse> getAllTodos(String operation, String search, String dateStr) {
		
		  GenericResponse<TodoResponse> response = new GenericResponse<>();
	        try {
	        	List<Todo> todos;
	        	  if (search != null && !search.isEmpty() && dateStr != null && !dateStr.isEmpty()) {
	                  LocalDate date = LocalDate.parse(dateStr);
	                  todos = todoRepository.findByTitleContainingIgnoreCaseAndCreatedAt(search, date);
	              } else if (search != null && !search.isEmpty()) {
	                  todos = todoRepository.findByTitleContainingIgnoreCase(search);
	              } else if (dateStr != null && !dateStr.isEmpty()) {
	                  LocalDate date = LocalDate.parse(dateStr);
	                  todos = todoRepository.findByCreatedAt(date);
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
