package com.ant.business;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="tb_sequence" )
public class TbSequence
{
	@Id
	private String name ;
	private int current_value;
	private int increment;

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getCurrent_value()
	{
		return current_value;
	}
	public void setCurrent_value(int current_value)
	{
		this.current_value = current_value;
	}
	
	@Column(name="_increment")
	public int getIncrement()
	{
		return increment;
	}
	public void setIncrement(int increment)
	{
		this.increment = increment;
	}
	
	
}
