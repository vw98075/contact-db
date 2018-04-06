package com.tideworks.contacts.interview;

public class ContastSpecification  Specification<Contact> {

private Query criterias;


@Override
public Predicate toPredicate
        (Root<User> root,CriteriaQuery<?> query,CriteriaBuilder builder){

        if(criteria.getOperation().equalsIgnoreCase(">")){
        return builder.greaterThanOrEqualTo(
        root.<String> get(criteria.getKey()),criteria.getValue().toString());
        }
        else if(criteria.getOperation().equalsIgnoreCase("<")){
        return builder.lessThanOrEqualTo(
        root.<String> get(criteria.getKey()),criteria.getValue().toString());
        }
        else if(criteria.getOperation().equalsIgnoreCase(":")){
        if(root.get(criteria.getKey()).getJavaType()==String.class){
        return builder.like(
        root.<String>get(criteria.getKey()),"%"+criteria.getValue()+"%");
        }else{
        return builder.equal(root.get(criteria.getKey()),criteria.getValue());
        }
        }
        return null;
        }


        }
