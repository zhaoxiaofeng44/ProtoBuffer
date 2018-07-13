package com.proto;

import java.util.HashMap;

public class Value{
	
	public int id;
	public String name;
	
	public int valueSize;
	public int valueType;
	public int valueCount;
	public HashMap<String,Value> children = null;
	
	public Value get(String name){
		return null == children ? null : children.get(name); 
	}
	
	public int _intValue;
	
}
