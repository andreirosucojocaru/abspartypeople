package com.abslbs.abspartypeople.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.abslbs.abspartypeople.domain.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByCredential(String credential);

}
