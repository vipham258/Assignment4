package com.algonquincollege.cst8277.assignment4.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-11-07T11:25:04.936-0500")
@StaticMetamodel(AccountBase.class)
public class AccountBase_ extends ModelBase_ {
	public static volatile SingularAttribute<AccountBase, Integer> id;
	public static volatile ListAttribute<AccountBase, User> users;
	public static volatile SingularAttribute<AccountBase, Portfolio> portfolio;
}
