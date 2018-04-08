package com.tideworks.contacts.interview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPAIntegrationTest {

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
        repository.save(userJohn);

        userTom = new Contact();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setPhone("222-444-1144");
        userTom.setAddress("2nd Street, Town Two");
        repository.save(userTom);
    }

    @Test
    public void allContacts(){

        Iterable<Contact> contactList = repository.findAll();
        assertThat("The full contact list", contactList.spliterator().getExactSizeIfKnown() == 2);
    }

    @Test
    public void findByEmail() {
        List<Contact> contactList = repository.findByEmail(userJohn.getEmail());
        assertThat("John's email", contactList.get(0).getEmail().equals(userJohn.getEmail()));
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        ContactSpecification spec =
                new ContactSpecification(new SearchCriteria("lastName", SearchOperation.getSimpleOperation(':'), "Doe"));

        List<Contact> results = repository.findAll(spec);

        assertThat(userJohn, isIn(results));
        assertThat(userTom, isIn(results));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        ContactSpecification spec1 =
                new ContactSpecification(new SearchCriteria("firstName", SearchOperation.getSimpleOperation(':'), "John"));
        ContactSpecification spec2 =
                new ContactSpecification(new SearchCriteria("lastName", SearchOperation.getSimpleOperation(':'), "Doe"));

        List<Contact> results = repository.findAll(Specifications.where(spec1).and(spec2));

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        ContactSpecification spec1 =
                new ContactSpecification(new SearchCriteria("phone", ':', "222-444-1111"));
        ContactSpecification spec2 =
                new ContactSpecification(new SearchCriteria("lastName", ':', "Doe"));

        List<Contact> results =
                repository.findAll(Specifications.where(spec1).and(spec2));

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        ContactSpecification spec1 =
                new ContactSpecification(new SearchCriteria("firstName", ':', "Adam"));
        ContactSpecification spec2 =
                new ContactSpecification(new SearchCriteria("lastName", ':', "Fox"));

        List<Contact> results =
                repository.findAll(Specifications.where(spec1).and(spec2));

        assertThat(userJohn, not(isIn(results)));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        ContactSpecification spec =
                new ContactSpecification(new SearchCriteria("firstName", ':', "Jo"));

        List<Contact> results = repository.findAll(spec);

        assertThat(userJohn, not(isIn(results)));
        assertThat(userTom, not(isIn(results)));
    }
}
