package com.proto;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

/*public class Buffer {
public Buffer(byte[] v,ProtoBuffer aa) {
	set(v);
}

public byte[] get() {
	return _bytes;
}

public int put(byte[] v) {
	_bytes = v;
}

public int size() {
	return _bytes.length;
}

protected byte[] _bytes;
}*/

/*class ProtoBuffer {


	
	
	public ProtoBuffer(int capacity){
		
		_buffer = new Buffers(capacity);
		_bufferOffsets = new HashMap<Integer, Integer>();
	}
	
	public byte[] get(int offset) {

		Buffer buffer = _buffers.get(offset);
		return null != buffer ? buffer.get() : null;
	}

	public int put(int offset, byte[] v) {

		_buffers.put(offset, new Buffer(v));
	}

	
	public int putShort(short s) {
		
		Utils.putShort(_buffer.get(),offset(id,2) , s);
	}

	public int putInt(int x) {

		Utils.putInt(_buffer.get(),offset(id,2), x);
	}

	public int putLong(long x) {

		byte[] bytes = new byte[8];
		Utils.putLong(bytes, 0, x);
		set(offset, bytes);
	}

	public int putChar(char ch) {

		byte[] bytes = new byte[2];
		Utils.putChar(bytes, 0, ch);
		set(offset, bytes);
	}

	public int putFloat(float x) {

		byte[] bytes = new byte[4];
		Utils.putFloat(bytes, 0, x);
		set(offset, bytes);
	}

	public int putDouble(double x) {

		byte[] bytes = new byte[8];
		Utils.putDouble(bytes, 0, x);
		set(offset, bytes);

	}

	public int putBoolean(boolean x) {

		byte[] bytes = new byte[1];
		Utils.putBoolean(bytes, 0, x);
		set(offset, bytes);

	}

	public int putString(String x) {

		int len = x.getBytes().length;
		byte[] bytes = new byte[len + 4];
		Utils.putInt(bytes, 0, len);
		Utils.putString(bytes, 4, x);
		set(offset, bytes);
	}

	int size() {

		int len = 0;
		for (Buffer v : _buffers.values()) {
			len += v.size();
		}
		return len;
	}

	
	protected int offset(int id,int size) {
		
		int offset = _buffer.offset();
		_buffer.capacity(offset + size);
		_bufferOffsets.put(id, offset);
		return offset;
	}

	protected Buffers _buffer;
	protected HashMap<Integer, Integer> _bufferOffsets;
}*/

public class Builder {

	public class ProtoBuffer {

		public ProtoBuffer(int capacity) {

			_offset = 0;
			_bytes = new byte[capacity];
		}

		public byte[] get() {

			return _bytes;
		}

		public int offset() {

			return _offset;
		}

		public void offset(int pos) {

			_offset = pos;
			capacity(pos);
		}

		public int capacity() {

			return _bytes.length;
		}

		public void capacity(int capacity) {

			if (capacity > _bytes.length) {
				_bytes = Arrays.copyOf(_bytes, capacity);
			}
		}

		public int putShort(short x) {

			int offset = _offset;
			_offset += 2;
			setShort(offset, x);
			return offset;
		}

		public int setShort(int offset, short x) {

			int newOffset = offset + 2;
			capacity(newOffset);
			Utils.putShort(_bytes, offset, x);
			return newOffset;
		}

		public int putInt(int x) {

			int offset = _offset;

			_offset = setInt(offset, x);
			return offset;
		}

		public int setInt(int offset, int x) {

			int newOffset = offset + 4;
			capacity(newOffset);
			Utils.putInt(_bytes, offset, x);
			return newOffset;
		}

		public int putLong(long x) {

			int offset = _offset;

			offset(offset + 8);
			Utils.putLong(_bytes, offset, x);
			return offset;
		}

		public void setLong(int offset, long x) {

			capacity(offset + 8);
			Utils.putLong(_bytes, offset, x);
		}

		public int putChar(char x) {

			int offset = _offset;

			offset(offset + 2);
			Utils.putChar(_bytes, offset, x);
			return offset;
		}

		public void setChar(int offset, char x) {

			capacity(offset + 2);
			Utils.putChar(_bytes, offset, x);
		}

		public int putFloat(float x) {

			int offset = _offset;

			offset(offset + 4);
			Utils.putFloat(_bytes, offset, x);
			return offset;
		}

		public int putDouble(double x) {

			int offset = _offset;

			offset(offset + 8);
			Utils.putDouble(_bytes, offset, x);
			return offset;
		}

		public int putBoolean(boolean x) {

			int offset = _offset;

			offset(offset + 1);
			Utils.putBoolean(_bytes, offset, x);
			return offset;
		}

		public int putString(String x) {

			int offset = _offset;
			_offset = setString(offset, x);
			return offset;
		}

		public int setString(int offset, String x) {

			byte[] bytes = x.getBytes();
			int len = bytes.length;
			offset = setInt(offset, len);

			int newOffset = offset + len;
			capacity(newOffset);
			Utils.putBytes(_bytes, offset, bytes);
			return newOffset;
		}

		public int putByte(byte x) {

			int offset = _offset;

			offset(offset + 1);
			_bytes[offset] = x;

			return offset;
		}

		public int setByte(int offset, byte x) {

			int newOffset = offset + 1;

			capacity(newOffset);
			_bytes[offset] = x;

			return newOffset;
		}

		public int putBytes(byte[] x) {

			int offset = _offset;

			int len = x.length;
			offset(offset + len);
			Utils.putBytes(_bytes, offset, x);
			return offset;
		}

		public int putBytes(byte x, int len) {

			int offset = _offset;

			offset(offset + len);
			Utils.putBytes(_bytes, offset, (byte) 0, len);
			return offset;
		}

		protected int _offset;
		protected byte[] _bytes;
	}

	public Builder(Value value) {

		_protoSize = value.valueSize;
		_protoCount = value.valueCount;

		_value = value;
		_proto = value.children;
		_buffer = new ProtoBuffer(1000);

		_looseSlot = 100;
		_looseLast = 400;
		// _buffer.putBytes((byte)0,_looseSlot);
		_buffer.offset(_looseSlot + 1);
		_buffer.setByte(_looseSlot,(byte) _looseSlot);
	}

	public int offset(String name) {

		Value value = _proto.get(name);
		return null != value ? value.id : 0;
	}

	public void putInt(int order, int value) {

		putOffset(order, _buffer.putInt(value), 1);
	}

	public void putBoolean(int order, boolean value) {

		putOffset(order, _buffer.putBoolean(value), 1);
	}

	public void putString(int order, String value) {

		int looseLast = _looseLast;
		_looseLast = _buffer.setString(_looseLast, value);
		putOffset(order, _buffer.putInt(looseLast - _looseSlot), 3);
	}

	public void putStruct(int order, int offset, int type) {

		
	}

	protected void putOffset(int order, int offset, int type) {
		
		if (order > _protoSlot) {
			_protoSlot = order;
		}
		// _bufferOffsets.put(order, type);
		_buffer.setByte(order, (byte) (offset - _looseSlot));
	}

	public int getSlot() {

		return _protoSlot;
	}

	public byte[] getBytes() {

		return _buffer.get();
	}

	/*
	 * public int getSize(){
	 * 
	 * int byteSize = _protoCount + 1; for (Value v : _proto.values()) { byte[]
	 * bytes = _buffer.get(v.id); if (null != bytes) { if (v.valueType < 3) {
	 * byteSize += bytes.length; } else { byteSize += bytes.length + 4; } } }
	 * return byteSize; }
	 */

	public void build(ByteBuffer buffers, int pos) {

		/*
		 * int bufferSize = 1000;// getSize(); int slot = pos + _protoCount;
		 * 
		 * buffers.position(slot); buffers.put((byte) _protoCount);
		 * 
		 * int cusor = 1; int mark = pos + bufferSize; for (Value v :
		 * _proto.values()) { byte[] bytes = _buffer.get(v.id); if (null ==
		 * bytes) { buffers.position(pos + v.id); buffers.put((byte) 0); } else
		 * { buffers.position(pos + v.id); buffers.put((byte) cusor); if
		 * (v.valueType < 3) { buffers.position(cusor + slot);
		 * buffers.put(bytes, 0, bytes.length); cusor += bytes.length; } else if
		 * (v.valueType == 3) { mark = mark - bytes.length;
		 * buffers.position(mark); buffers.put(bytes, 0, bytes.length);
		 * 
		 * byte[] bytes2 = new byte[4]; Utils.putInt(bytes2, 0, mark - slot);
		 * 
		 * buffers.position(cusor + slot); buffers.put(bytes2, 0,
		 * bytes2.length);
		 * 
		 * cusor += 4; } else if (v.valueType == 4) { mark = mark -
		 * bytes.length; buffers.position(mark); buffers.put(bytes, 0,
		 * bytes.length);
		 * 
		 * byte[] bytes2 = new byte[4]; Utils.putInt(bytes2, 0, mark - slot +
		 * v.valueCount);
		 * 
		 * buffers.position(cusor + slot); buffers.put(bytes2, 0,
		 * bytes2.length);
		 * 
		 * cusor += 4; } } }
		 */
	}

	protected int _looseSlot;
	protected int _looseLast;
	protected int _protoSlot;
	protected HashMap<Integer, Integer> _bufferOffsets;

	protected ProtoBuffer _buffer;
	protected int _protoSize;
	protected int _protoCount;
	protected Value _value;
	protected HashMap<String, Value> _proto;

	public static void main(String[] args) {

		/*
		 * HashMap<String, Value> ch = new HashMap<String, Value>();
		 * 
		 * Value aac = new Value(); aac.id = 0; aac.name = "idc"; aac.valueType
		 * = 1; ch.put("idc", aac);
		 * 
		 * Value ccc = new Value(); ccc.id = 1; ccc.name = "namec";
		 * ccc.valueType = 3; ch.put("namec", ccc);
		 * 
		 * HashMap<String, Value> children = new HashMap<String, Value>();
		 * 
		 * Value aa = new Value(); aa.id = 0; aa.name = "id"; aa.valueType = 1;
		 * children.put("id", aa);
		 * 
		 * Value bb = new Value(); bb.id = 1; bb.name = "bool"; bb.valueType =
		 * 2; children.put("bool", bb);
		 * 
		 * Value cc = new Value(); cc.id = 2; cc.name = "name"; cc.valueType =
		 * 3; children.put("name", cc);
		 * 
		 * Value dd = new Value(); dd.id = 3; dd.name = "obj"; dd.valueType = 4;
		 * dd.valueSize = 2; dd.valueCount = 2; dd.children = ch;
		 * children.put("obj", dd);
		 * 
		 * Value ee = new Value(); ee.id = 1; ee.name = "main"; ee.valueType =
		 * 0; ee.valueSize = 4; ee.valueCount = 4; ee.children = children;
		 * 
		 * Builder f = new Builder(dd); f.setInt("idc", 5245);
		 * f.setString("namec", "hellow world");
		 * 
		 * ByteBuffer bufff = ByteBuffer.allocate(11256); f.build(bufff, 0);
		 * 
		 * byte[] hhf = new byte[f.getSize()]; bufff.position(0); bufff.get(hhf,
		 * 0, f.getSize());
		 * 
		 * Builder a = new Builder(ee); a.setInt("id", 5245);
		 * a.setBoolean("bool", false); a.setString("name",
		 * "testc c aa xxx aaa xxx"); a.setBytes("obj", hhf);
		 * 
		 * ByteBuffer buff = ByteBuffer.allocate(11256); a.build(buff, 0);
		 * 
		 * byte[] hh = new byte[a.getSize()]; buff.position(0); buff.get(hh, 0,
		 * a.getSize()); System.out.print(a.getSize() + "\n");
		 * System.out.print(buff.position() + "\n"); System.out.print(hh +
		 * "\n");
		 * 
		 * Reader r = new Reader(ee); r.setBytes(hh, 4);
		 * 
		 * System.out.print("  >>>> r " + r.getInt("id") + " >>  " +
		 * r.getBoolean("bool") + " >> " + r.getString("name") + " >> obj " +
		 * r.getObject("obj").getString("namec") + "\n");
		 */

		Value aac = new Value();
		aac.id = 0;
		aac.name = "idc";
		aac.valueType = 1;

		Builder f = new Builder(aac);
		f.putInt(0, 5245);
		f.putString(1, "aacc test aa ");
		byte[] by = f.getBytes();

		Reader r = new Reader(aac);
		r.setBytes(by, 100);

		System.out.print("  >>>> r " + by + " >>  " + r.getInt(0) + " ccc  >>  " + r.getString(1));

	}

}
