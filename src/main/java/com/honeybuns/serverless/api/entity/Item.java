/**
 * 
 */
package com.honeybuns.serverless.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author shardulsrivastava
 *
 */
@Entity(name = "course_registration")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "unitPrice")
	private String unitPrice;

	@Column(name = "wholeSaleMin")
	private String wholeSaleMin;
}
