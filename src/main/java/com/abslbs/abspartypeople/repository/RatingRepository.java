package com.abslbs.abspartypeople.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.abslbs.abspartypeople.domain.Rating;

@Transactional
public interface RatingRepository extends CrudRepository<Rating, Long> {

}
