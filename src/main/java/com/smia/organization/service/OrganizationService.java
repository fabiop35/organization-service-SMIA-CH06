package com.smia.organization.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brave.ScopedSpan;
import brave.Tracer;

import lombok.extern.slf4j.Slf4j;

import com.smia.organization.model.Organization;
import com.smia.organization.repository.OrganizationRepository;
import com.smia.organization.jms.source.SimpleSourceBean;

@Slf4j
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    SimpleSourceBean simpleSourceBean;

    @Autowired
    Tracer tracer;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = null;
        ScopedSpan newSpan = tracer.startScopedSpan("getOrgDBCall");
        try {
            opt = repository.findById(organizationId);
            simpleSourceBean.publishOrganizationChange("GET", organizationId);

            if (!opt.isPresent()) {
                String message = String.format("Unable to find anorganization with theOrganization id %s", organizationId);
                log.error(message);
                throw new IllegalArgumentException(message);
            }
            log.debug("Retrieving Organization Info: " + opt.get().toString());
        } finally {
            newSpan.tag("peer.service", "postgres");
            newSpan.annotate("Client received");
            newSpan.finish();
        }
        return opt.get();
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
