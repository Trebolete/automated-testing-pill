package com.allianz.automated.testing.repository;


import com.allianz.automated.testing.model.Policy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends CrudRepository<Policy, String> {
}