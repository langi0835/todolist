package todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import todo.model.entity.Todo;
import todo.service.TodoService;

@Api(tags = "Todo list 相關api")
@RestController
@RequestMapping("/api")
public class TodoController {

	@Autowired
	TodoService todoService; // 取得Service物件

	@ApiOperation("取得所有代辦事項列表")
	@GetMapping("/todos")
	@ApiResponses({ @ApiResponse(code = 401, message = "沒有權限"), @ApiResponse(code = 404, message = "找不到路徑") })
	public ResponseEntity<Iterable<Todo>> getTodos() {
		Iterable<Todo> todoList = todoService.getTodos();
		return ResponseEntity.status(HttpStatus.OK).body(todoList);
	}

	@GetMapping("/todos/{id}")
	public Optional<Todo> getTodo(@PathVariable Integer id) {
		Optional<Todo> todo = todoService.findById(id);
		return todo;
	}

	@PostMapping("/todos")
	public ResponseEntity<Integer> createTodo(@RequestBody Todo todo) {
		Integer rlt = todoService.createTodo(todo);
		return ResponseEntity.status(HttpStatus.CREATED).body(rlt);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<String> upadteTodo(@PathVariable Integer id, @RequestBody Todo todo) {
		Boolean rlt = todoService.updateTodo(id, todo);
		if (!rlt) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status 欄位不能為空");
		}
		return ResponseEntity.status(HttpStatus.OK).body("");
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable Integer id) {
		Boolean rlt = todoService.deleteTodo(id);
		if (!rlt) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id 不存在");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}

}
