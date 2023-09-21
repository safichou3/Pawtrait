package com.studiogoat.pawtrait.repository;

import com.studiogoat.pawtrait.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {}
