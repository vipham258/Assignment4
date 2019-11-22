package com.algonquincollege.cst8277.assignment4.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-11-21T22:28:05.417-0500")
@StaticMetamodel(ModelBase.class)
public class ModelBase_ {
	public static volatile SingularAttribute<ModelBase, Integer> version;
	public static volatile SingularAttribute<ModelBase, Integer> id;
	public static volatile SingularAttribute<ModelBase, Double> balance;
	public static volatile SingularAttribute<ModelBase, LocalDateTime> createDate;
	public static volatile SingularAttribute<ModelBase, LocalDateTime> updateDate;
}
