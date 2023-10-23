package com.smia.organization.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smia.organization.model.Organization;
import com.smia.organization.repository.OrganizationRepository;
import com.smia.organization.jms.source.SimpleSourceBean;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }

    public Organization findByName(String name) {

        Optional<Organization> org = repository.findByName(name);
        return (org.isPresent()) ? org.get() : null;
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED.toString(), organization.getId());
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED.toString(), organization.getId());
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        simpleSourceBean.publishOrganizationChange(ActionEnum.DELETED.toString(), organization.getId());
    }

    public enum ActionEnum {
        GET,
        CREATED,
        UPDATED,
        DELETED
    }
}
