package com.example.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.User;
import com.example.course.repositories.UserRepository;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;


@Service
public class UserService {
	
	
	
	@Autowired
	private UserRepository repository;
	
	
	public List<User> FindAll(){
		return repository.findAll();
	}
	
	
	public User FindById (Long id ) {
		Optional <User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e){
			throw new  DatabaseException(e.getMessage());		
			}
}
	
	public User update(Long id, User obj) {
		User entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(obj);
	}


	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		// TODO Auto-generated method stub
		
	}

}