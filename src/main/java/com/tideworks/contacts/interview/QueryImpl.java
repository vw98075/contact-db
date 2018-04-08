package com.tideworks.contacts.interview;


import java.util.ArrayList;
import java.util.List;

public class QueryImpl implements Query {

    private List<SearchCriteria> criteriaList;

    public QueryImpl(){
        this.criteriaList = new ArrayList<>();
    }

    public boolean addCriteria(final String key, final SearchOperation operation, final Object value){
        return this.criteriaList.add(new SearchCriteria(key, operation, value));
    }

    public boolean addCriteria(final String key, final char operation, final Object value){
        return this.criteriaList.add(new SearchCriteria(key, operation, value));
    }

    public List<SearchCriteria> getSearchCriteriaList() {
        return this.criteriaList;
    }
}
