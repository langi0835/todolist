package todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import todo.model.dao.TodoDao;
import todo.model.entity.Todo;
import todo.service.TodoService;

@SpringBootTest
public class TestTodoService {
	@Autowired
	TodoService todoService;

	@MockBean // 表示Mockito會幫我們創造一個假的對象，替換真實的Dao
	TodoDao todoDao;

	@Test
	public void testGetTodos() {
		List<Todo> expectedTodosList = new ArrayList();
		Todo todo = new Todo();
		todo.setId(1);
		todo.setTask("喝咖啡");
		todo.setStatus(1);
		expectedTodosList.add(todo);

		// 定義模擬呼叫todoDao.findAll() 要回傳的預設結果
		Mockito.when(todoDao.findAll()).thenReturn(expectedTodosList);

		// [Act]操作todoService.getTodos();
		Iterable<Todo> actualTodoList = todoService.getTodos();

		// [Assert] 預期與實際的資料
		assertEquals(expectedTodosList, actualTodoList);
	}

	@Test
	public void testCreateTodo() {
		
		Todo todo = new Todo();
		todo.setId(1);
		todo.setTask("讀Java");
		todo.setStatus(1);
		
		Mockito.when(todoDao.save(todo)).thenReturn(todo);
		
		Integer actualId = todoService.createTodo(todo);
		
		assertEquals(1,actualId);
	}
	
	@Test
	public void testUpdateTodoSuccess() {
		Todo todo = new Todo();
		todo.setId(1);
		todo.setTask("裝文青");
		todo.setStatus(1);
		Optional<Todo> resTodo = Optional.of(todo);
		
		Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
		
		todo.setStatus(2);
		
		Boolean actualUpdateRlt = todoService.updateTodo(1, todo);
		
		assertEquals(true,actualUpdateRlt);
	}
	
	@Test
	public void testUpdateTodoExistId() {
		Todo todo = new Todo();
		todo.setStatus(2);
		
		Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());
		
		Boolean actualUpdateRlt = todoService.updateTodo(100, todo);
		
		assertEquals(false,actualUpdateRlt);
	}
	
	@Test
	public void testUpdateTodoOccurException() {
		Todo todo = new Todo();
		todo.setId(1);
		todo.setStatus(1);
		Optional<Todo> resTodo = Optional.of(todo);
		
		Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
		todo.setStatus(2);
		
		doThrow(NullPointerException.class).when(todoDao).save(todo);
		
		Boolean actualUpdateRlt = todoService.updateTodo(100, todo);
		
		assertEquals(false,actualUpdateRlt);
	}
	
	@Test
	public void testDeleteTodoSuccess () {
	    //準備更改的資料
	    Todo todo = new Todo();
	    todo.setId(1);
	    todo.setTask("鐵人賽文章");
	    todo.setStatus(2);
	    Optional<Todo> resTodo = Optional.of(todo);

	    // 模擬呼叫todoDao.findById(id)，模擬資料庫有id=1的資料
	    Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

	    // [Act] 實際呼叫操作todoService.deleteTodo()
	    Boolean actualDeleteRlt = todoService.deleteTodo(1);

	    //  [Assert] 預期與實際的資料
	    assertEquals(true, actualDeleteRlt);
	 }

	@Test
	public void testDeleteTodoIdNotExist () {
	    //準備更改的資料
	    Todo todo = new Todo();
	    todo.setId(1);
	    todo.setTask("鐵人賽文章");
	    todo.setStatus(2);
	    Optional<Todo> resTodo = Optional.of(todo);

	    // 模擬呼叫todoDao.findById(id)，並模擬資料庫沒有id=100的資料
	    Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

	    // [Act] 實際呼叫操作todoService.deleteTodo()
	    Boolean actualDeleteRlt = todoService.deleteTodo(100);

	    //  [Assert] 預期與實際的資料
	    assertEquals(false, actualDeleteRlt);
	}

	@Test
	public void testDeleteTodoOccurException () {
	    //準備更改的資料
	    Todo todo = new Todo();
	    todo.setId(1);
	    todo.setTask("鐵人賽文章");
	    todo.setStatus(2);
	    Optional<Todo> resTodo = Optional.of(todo);

	    // 模擬呼叫todoDao.findById(id)，並模擬資料庫有id=1的資料
	    Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

	    // 模擬呼叫todoDao.deleteById(id)，會發生NullPointerException
	    doThrow(NullPointerException.class).when(todoDao).deleteById(1);

	    // [Act] 實際呼叫操作todoService.deleteTodo()
	    Boolean actualDeleteRlt = todoService.deleteTodo(1);

	    //  [Assert] 預期與實際的資料
	    assertEquals(false, actualDeleteRlt);
	}
}
