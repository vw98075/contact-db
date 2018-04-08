package com.tideworks.contacts.interview;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class ContactSpecificationsBuilder {

    private List<SearchCriteria> params;

    public ContactSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public ContactSpecificationsBuilder(List<SearchCriteria> params) {
        this.params = params;
    }

    public final ContactSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final ContactSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Contact> build() {

        if (params.size() == 0)
            return null;

        Specification<Contact> result = new ContactSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specifications.where(result)
                    .or(new ContactSpecification(params.get(i)))
                    : Specifications.where(result)
                    .and(new ContactSpecification(params.get(i)));

        }
        return result;
    }

    public final ContactSpecificationsBuilder with(ContactSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final ContactSpecificationsBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
