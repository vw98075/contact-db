package com.tideworks.contacts.interview;

/**
 * Data store that will persist {@link Contact} entities
 *
 * @author justin.hanney (jhanney)
 * @since 0.1.0
 */
public interface ContactsDataStore {

    /**
     * Deletes a {@link Contact} from the data store
     *
     * @param contact {@link Contact} to delete
     */
    void delete(final Contact contact);

    /**
     * Inserts a new {@link Contact} into the data store
     *
     * @param contact {@link Contact} to insert
     * @return {@link Contact} that was just inserted
     */
    Contact insert(final Contact contact);

    /**
     * Selects a single {@link Contact} based on the supplied Query
     *
     * @param criteria {@link Query} to select the Contact
     * @return {@link Contact} that matches the Query
     */
    Contact selectOne(final Query criteria);

    /**
     * Updates a given {@link Contact}
     *
     * @param contact {@link Contact} to update
     * @return {@link Contact} that was just updated
     */
    Contact update(final Contact contact);

}
