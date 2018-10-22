package com.abslbs.abspartypeople.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.abslbs.abspartypeople.domain.Attachment;

@Transactional
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
