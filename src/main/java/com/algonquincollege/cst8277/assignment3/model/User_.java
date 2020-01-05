package com.algonquincollege.cst8277.assignment3.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-12-16T15:18:13.013-0500")
@StaticMetamodel(User.class)
public class User_ extends ModelBase_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile ListAttribute<User, AccountBase> accounts;
}
