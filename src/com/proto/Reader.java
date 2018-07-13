package com.proto;

import java.util.HashMap;

public class Reader {

	public Reader(Value value){
		
		this(value,null,0);
	}
	
	public Reader(Value value,byte[] v,int slot){
		
		_proto = value.children;
		
		setBytes(v,slot);
	}
	
	public int offset(String name){
		
		Value value = _proto.get(name);
		return null != value ? offset(value.id) : 0;
	}
	
	public int offset(int order){
		
		int offset = (_protoBytes[_protoSlot] - order);
		return _protoSlot + _protoBytes[_protoSlot - offset];
	}

	public void setBytes(byte[] v,int slot){
		
		_protoBytes = v;
		_protoSlot = slot;
	}

	public int getInt(String name){
		
		int address = offset(name);
		return 0 < address?  Utils.getInt(_protoBytes, address) : 0;
	}

	public int getInt(int order){
		
		int address = offset(order);
		return 0 < address?  Utils.getInt(_protoBytes, address) : 0;
	}
	
	public boolean getBoolean(String name){
		
		int address = offset(name);
		return 0 < address? Utils.getBoolean(_protoBytes, address) : false;   
	}
	
	public Reader getObject(String name){
		
		int address = offset(name);
		if(0 < address){
			int offset = _protoSlot + Utils.getInt(_protoBytes, address);
			return new Reader( _proto.get(name),_protoBytes,offset);
		}
		return null;
	}
	
	public String getString(String name){
		
		int address = offset(name);
		if(0 < address){
			int slot = _protoSlot + Utils.getInt(_protoBytes, address);
			int size = Utils.getInt(_protoBytes, slot);
			return Utils.getString(_protoBytes, slot + 4, size);
		}
		return "";
	}
	
	
	public String getString(int order){
		
		int address = offset(order);
		if(0 < address){
			int slot = _protoSlot + Utils.getInt(_protoBytes, address);
			int size = Utils.getInt(_protoBytes, slot);
			return Utils.getString(_protoBytes, slot + 4, size);
		}
		return "";
	}

	protected int _protoSlot;
	protected byte[] _protoBytes;
	protected int _protoSize;
	protected int _protoCount;
	protected HashMap<String,Value> _proto;
	
}