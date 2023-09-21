package com.studiogoat.pawtrait.repository;

import com.studiogoat.pawtrait.domain.Like;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends MongoRepository<Like, String> {}
