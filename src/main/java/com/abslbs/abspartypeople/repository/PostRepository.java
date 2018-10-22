package com.abslbs.abspartypeople.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.abslbs.abspartypeople.domain.Post;

@Transactional
public interface PostRepository extends CrudRepository<Post, Long> {
	
	@Query("select p from Post p order by p.createdAt desc")
	Iterable<Post> findAllInReverseChronologicalOrder();
}
