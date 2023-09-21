package com.studiogoat.pawtrait.repository;

import com.studiogoat.pawtrait.domain.PhotoTag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PhotoTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoTagRepository extends MongoRepository<PhotoTag, String> {}
