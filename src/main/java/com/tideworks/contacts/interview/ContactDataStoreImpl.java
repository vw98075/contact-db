package com.tideworks.contacts.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContactDataStoreImpl implements ContactsDataStore {

    @Autowired
    private ContactRepository repository;

    public void delete(final Contact contact) {
        repository.delete(contact);
    };

    public Contact insert(final Contact contact){
        List<Contact> contactList = repository.findByEmail(contact.getEmail());
        if(!contactList.isEmpty())
            return null;
        return repository.save(contact);
    };

    public Contact selectOne(final Query criteria){

        QueryImpl q = (QueryImpl) criteria;
        ContactSpecificationsBuilder builder = new ContactSpecificationsBuilder(q.getSearchCriteriaList());
        Specification<Contact> spec = builder.build();
        Iterable<Contact> contactIterable = repository.findAll(spec);
        return ((List<Contact>) contactIterable).isEmpty() ? null : ((List<Contact>) contactIterable).get(0);
    }

    public Contact update(final Contact contact){
        List<Contact> contactList = repository.findByEmail(contact.getEmail());
        if(contactList.isEmpty())
            return null;
        return repository.save(contact);
    }
}
