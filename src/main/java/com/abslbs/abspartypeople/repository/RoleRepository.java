package com.abslbs.abspartypeople.repository;

import org.springframework.data.repository.CrudRepository;

import com.abslbs.abspartypeople.domain.Role;

public interface RoleRepository  extends CrudRepository<Role, Long> {
	
	Role findByName(String name);

}
