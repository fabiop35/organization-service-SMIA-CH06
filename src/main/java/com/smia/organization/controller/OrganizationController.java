package com.smia.organization.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import com.smia.organization.model.Organization;
import com.smia.organization.service.OrganizationService;

@Slf4j
@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @RolesAllowed({"ADMIN", "USER", "ostock-admin"})
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        log.info("~~~>OrganizationController.GET.@organizationId: " + organizationId);
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganizationByName(@PathVariable("name") String name) {

        log.info("### Controller.getOrganizatiinByName()");
        return ResponseEntity.ok(service.findByName(name));

    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String id, @RequestBody Organization organization) {
        service.update(organization);
    }

    @RolesAllowed({"ADMIN", "USER", "ostock-admin"})
    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(service.create(organization));
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("id") String id, @RequestBody Organization organization) {
        service.delete(organization);
    }

}
