package todo.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Entity
@Table
@Data
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String task = "";
	
	@Column(insertable = false,columnDefinition = "int default 1")
	Integer status;
	
	@CreatedDate
	@Column(updatable = false, nullable = false)
	Date createTime = new Date();
	
	@LastModifiedDate
	@Column(nullable = false)
	Date updateTime = new Date();
	
}
