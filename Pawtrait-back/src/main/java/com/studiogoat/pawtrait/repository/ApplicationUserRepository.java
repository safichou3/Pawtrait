package com.studiogoat.pawtrait.repository;

import com.studiogoat.pawtrait.domain.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ApplicationUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {}
