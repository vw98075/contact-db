package com.tideworks.contacts.interview;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> , JpaSpecificationExecutor<Contact> {

    List<Contact> findByEmail(String email);

}
