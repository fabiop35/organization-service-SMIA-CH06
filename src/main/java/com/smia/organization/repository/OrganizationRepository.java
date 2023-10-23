package com.smia.organization.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smia.organization.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    @Override
    public Optional<Organization> findById(String organizationId);

    public Optional<Organization> findByName(String name);
}
