package todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todo.model.dao.TodoDao;
import todo.model.entity.Todo;

@Service
public class TodoService {

	// 可以讓Spring自動把屬性需要的對象從Spring容器找出来，並注入给該屬性
	@Autowired
	TodoDao todoDao;

	public Iterable<Todo> getTodos() {
		return todoDao.findAll();
	}

	public Integer createTodo(Todo todo) {
		Todo rltTodo = todoDao.save(todo);
		return rltTodo.getId();
	}

	public Boolean updateTodo(Integer id, Todo todo) {
		Optional<Todo> isExistTodo = findById(id);
		if (!isExistTodo.isPresent()) {
			return false;
		}
		Todo newTodo = isExistTodo.get();
		if (todo.getStatus() == null) {
			return false;
		}

		newTodo.setStatus(todo.getStatus());
		todoDao.save(newTodo);
		return true;

	}

	public Optional<Todo> findById(Integer id) {

		var todo = todoDao.findById(id);
		return todo;
	}

	public Boolean deleteTodo(Integer id) {
		Optional<Todo> findTodo = findById(id);
		if (!findTodo.isPresent()) {
			return false;
		}
		try {
			todoDao.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
