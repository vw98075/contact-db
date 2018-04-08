package com.tideworks.contacts.interview;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactDataStoreTest {

    @Autowired
    private ContactsDataStore contactsDataStore;

    @Autowired
    private ContactRepository repository;

    private Contact userJohn;
    private Contact userTom;

    @Before
    public void init(){
        repository.deleteAll();

        userJohn = new Contact();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setPhone("222-444-1111");
        userJohn.setAddress("1st Road, Town One");

        userTom = new Contact();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setPhone("222-444-1144");
        userTom.setAddress("2nd Street, Town Two");
    }

    @Test
    public void insert(){
        Contact john = contactsDataStore.insert(userJohn);
        assertThat("After saving john", john.getEmail().equals(userJohn.getEmail()));
    }

    @Test
    public void insertAgain(){
        Contact john = contactsDataStore.insert(userJohn);
        john = contactsDataStore.insert(userJohn);
        assertThat("After saving john second time", john == null);
    }

    @Test
    public void update(){
        Contact john = contactsDataStore.insert(userJohn);
        john.setPhone("888-777-9999");
        john = contactsDataStore.update(john);
        assertThat("After updating John's email", john.getPhone().equals("888-777-9999"));
    }

    @Test
    public void updateNonexistingData(){
        Contact john = contactsDataStore.update(userJohn);
        assertThat("After saving john second time", john == null);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        populateDate();

        Query q = new QueryImpl();
        ((QueryImpl) q).addCriteria("lastName", SearchOperation.getSimpleOperation(':'), "Doe");

        Contact result = contactsDataStore.selectOne(q);

        assertThat("there is one", result != null);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {

        populateDate();

        Query q = new QueryImpl();
        ((QueryImpl) q).addCriteria("firstName", SearchOperation.getSimpleOperation(':'), "John");
        ((QueryImpl) q).addCriteria("lastName", SearchOperation.getSimpleOperation(':'), "Doe");

        Contact result = contactsDataStore.selectOne(q);
        assertThat("John is there", userJohn.equals(result));
        assertThat("Tom isn't there", !userTom.equals(result));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {

        populateDate();

        Query q = new QueryImpl();
        ((QueryImpl) q).addCriteria("phone", ':', "222-444-1111");
        ((QueryImpl) q).addCriteria("lastName", SearchOperation.getSimpleOperation(':'), "Doe");

        Contact result = contactsDataStore.selectOne(q);
        assertThat("John is there", userJohn.equals(result));
        assertThat("Tom isn't there", !userTom.equals(result));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {

        populateDate();

        Query q = new QueryImpl();
        ((QueryImpl) q).addCriteria("firstName", ':', "Adam");
        ((QueryImpl) q).addCriteria("lastName", ':', "Fox");

        Contact result = contactsDataStore.selectOne(q);
        assertThat("John isn't there", !userJohn.equals(result));
        assertThat("Tom isn't there", !userTom.equals(result));
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {

        populateDate();

        Query q = new QueryImpl();
        ((QueryImpl) q).addCriteria("firstName", ':', "Jo");

        Contact result = contactsDataStore.selectOne(q);
        assertThat("John isn't there", !userJohn.equals(result));
        assertThat("Tom isn't there", !userTom.equals(result));
    }

    private void populateDate(){
        repository.save(userJohn);
        repository.save(userTom);
    }
}
