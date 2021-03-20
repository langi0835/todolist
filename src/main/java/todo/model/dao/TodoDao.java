package todo.model.dao;

import org.springframework.data.repository.CrudRepository;

import todo.model.entity.Todo;

public interface TodoDao extends CrudRepository<Todo, Integer> {

}
