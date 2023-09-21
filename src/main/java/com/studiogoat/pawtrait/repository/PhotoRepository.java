package com.studiogoat.pawtrait.repository;

import com.studiogoat.pawtrait.domain.Photo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {}
