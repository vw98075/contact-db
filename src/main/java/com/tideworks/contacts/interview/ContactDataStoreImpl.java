package com.tideworks.contacts.interview;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContactDataStoreImpl implements ContactsDataStore {

    private Map<String, Contact> dataStorage = new HashMap<>();

    public void delete(final Contact contact) {
        dataStorage.remove(contact);
    }

    public Contact insert(final Contact contact) {

        if (dataStorage.containsKey(contact.getEmail()))
            return null;

        return dataStorage.put(contact.getEmail(), contact);
    }

    /**
     * Selects a single {@link Contact} based on the supplied Query
     *
     * @param criteria {@link Query} to select the Contact
     * @return {@link Contact} that matches the Query
     */
    public Contact selectOne(final Query criteria) {

        QueryImpl query = (QueryImpl) criteria;
        Optional<String> queryEmail = query.getEmail();
        if (queryEmail.isPresent()) {
            Contact contact = dataStorage.get(queryEmail.get());
            if (contact == null)
                return contact;

            Optional<String> queryFirstName = query.getFirstName();
            if (queryFirstName.isPresent() && contact.getFirstName() != queryFirstName.get()) {
                return null;
            }

            Optional<String> queryLastName = query.getLastName();
            if (queryLastName.isPresent() && contact.getLastName() != queryLastName.get()) {
                return null;
            }

            Optional<String> queryPhone = query.getPhone();
            if (queryPhone.isPresent() && contact.getPhone() != queryPhone.get()) {
                return null;
            }

            Optional<String> queryAddress = query.getAddress();
            if (queryAddress.isPresent() && contact.getAddress() != queryAddress.get()) {
                return null;
            }

            return contact;
        } else {

            Optional<String> queryFirstName = query.getFirstName();
            final Optional<String> queryLastName = query.getLastName();
            final Optional<String> queryPhone = query.getPhone();
            final Optional<String> queryAddress = query.getAddress();
            Collection<Contact> contacts = dataStorage.values();
            if (queryFirstName.isPresent() && queryLastName.isPresent() && queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryLastName.get().equals(m.getLastName()) &&
                                queryPhone.get().equals(m.getPhone()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

            // one search parameter isn't presented
            if (!queryFirstName.isPresent() && queryLastName.isPresent() && queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryLastName.get().equals(m.getLastName()) &&
                                queryPhone.get().equals(m.getPhone()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (queryFirstName.isPresent() && !queryLastName.isPresent() && queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryPhone.get().equals(m.getPhone()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (queryFirstName.isPresent() && queryLastName.isPresent() && !queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryLastName.get().equals(m.getLastName()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (queryFirstName.isPresent() && queryLastName.isPresent() && queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryLastName.get().equals(m.getLastName()) &&
                                queryPhone.get().equals(m.getPhone())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

            // two search parameters aren't presented
            if (!queryFirstName.isPresent() && !queryLastName.isPresent() && queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryPhone.get().equals(m.getPhone()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (!queryFirstName.isPresent() && queryLastName.isPresent() && !queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryLastName.get().equals(m.getLastName()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (!queryFirstName.isPresent() && queryLastName.isPresent() && queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryLastName.get().equals(m.getLastName()) &&
                                queryPhone.get().equals(m.getPhone())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

            if (queryFirstName.isPresent() && !queryLastName.isPresent() && !queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (queryFirstName.isPresent() && !queryLastName.isPresent() && queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryPhone.get().equals(m.getPhone())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (queryFirstName.isPresent() && queryLastName.isPresent() && !queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryFirstName.get().equals(m.getFirstName()) &&
                                queryLastName.get().equals(m.getLastName())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

            // three search parameters aren't presented
            if (!queryFirstName.isPresent() && !queryLastName.isPresent() && !queryPhone.isPresent() && queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryAddress.get().equals(m.getAddress())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }
            if (!queryFirstName.isPresent() && !queryLastName.isPresent() && queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryPhone.get().equals(m.getPhone())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

            if (!queryFirstName.isPresent() && queryLastName.isPresent() && !queryPhone.isPresent() && !queryAddress.isPresent()) {
                Optional<Contact> c = contacts.stream().filter(
                        m -> queryLastName.get().equals(m.getLastName())
                ).findFirst();
                return c.isPresent() ? c.get() : null;
            }

        }
        return null;

    }

    /**
     * Updates a given {@link Contact}
     *
     * @param contact {@link Contact} to update
     * @return {@link Contact} that was just updated
     */
    public Contact update(final Contact contact) {
        if (!dataStorage.containsKey(contact.getEmail()))
            return null;
        return dataStorage.put(contact.getEmail(), contact);
    }
}
