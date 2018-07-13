package com.proto;

public class Utils {

    /** 
     * ת��shortΪbyte 
     *  
     * @param b 
     * @param index
     * @param s 
     */  
    public static void putShort(byte b[], int index, short s) {  
        b[index + 1] = (byte) (s >> 8);  
        b[index + 0] = (byte) (s >> 0);  
    }  
  
    /** 
     * ͨ��byte����ȡ��short 
     *  
     * @param b 
     * @param index �ڼ�λ��ʼȡ 
     * @return 
     */  
    public static short getShort(byte[] b, int index) {  
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));  
    }  
  
    /** 
     * ת��intΪbyte���� 
     * 
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putInt(byte[] bb, int index, int x) {  
        bb[index + 3] = (byte) (x >> 24);  
        bb[index + 2] = (byte) (x >> 16);  
        bb[index + 1] = (byte) (x >> 8);  
        bb[index + 0] = (byte) (x >> 0);  
    }  
  
    /** 
     * ͨ��byte����ȡ��int 
     *  
     * @param bb 
     * @param index 
     *            �ڼ�λ��ʼ 
     * @return 
     */  
    public static int getInt(byte[] bb, int index) {  
        return (int) ((((bb[index + 3] & 0xff) << 24)  
                | ((bb[index + 2] & 0xff) << 16)  
                | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));  
    }  
  
    /** 
     * ת��long��Ϊbyte���� 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putLong(byte[] bb, int index, long x) {  
        bb[index + 7] = (byte) (x >> 56);  
        bb[index + 6] = (byte) (x >> 48);  
        bb[index + 5] = (byte) (x >> 40);  
        bb[index + 4] = (byte) (x >> 32);  
        bb[index + 3] = (byte) (x >> 24);  
        bb[index + 2] = (byte) (x >> 16);  
        bb[index + 1] = (byte) (x >> 8);  
        bb[index + 0] = (byte) (x >> 0);  
    }  
  
    /** 
     * ͨ��byte����ȡ��long 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static long getLong(byte[] bb, int index) {  
        return ((((long) bb[index + 7] & 0xff) << 56)  
                | (((long) bb[index + 6] & 0xff) << 48)  
                | (((long) bb[index + 5] & 0xff) << 40)  
                | (((long) bb[index + 4] & 0xff) << 32)  
                | (((long) bb[index + 3] & 0xff) << 24)  
                | (((long) bb[index + 2] & 0xff) << 16)  
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));  
    }  
  
    /** 
     * �ַ����ֽ�ת�� 
     *  
     * @param ch 
     * @return 
     */  
    public static void putChar(byte[] bb, int index, char ch) {  
        int temp = (int) ch;  
        // byte[] b = new byte[2];  
        for (int i = 0; i < 2; i ++ ) {  
            bb[index + i] = new Integer(temp & 0xff).byteValue(); // �����λ���������λ  
            temp = temp >> 8; // ������8λ  
        }  
    }  
  
    /** 
     * �ֽڵ��ַ�ת�� 
     *  
     * @param b 
     * @return 
     */  
    public static char getChar(byte[] b, int index) {  
        int s = 0;  
        if (b[index + 1] > 0)  
            s += b[index + 1];  
        else  
            s += 256 + b[index + 0];  
        s *= 256;  
        if (b[index + 0] > 0)  
            s += b[index + 1];  
        else  
            s += 256 + b[index + 0];  
        char ch = (char) s;  
        return ch;  
    }  
  
    /** 
     * floatת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putFloat(byte[] bb, int index, float x) {  
        // byte[] b = new byte[4];  
        int l = Float.floatToIntBits(x);  
        for (int i = 0; i < 4; i++) {  
            bb[index + i] = new Integer(l).byteValue();  
            l = l >> 8;  
        }  
    }  
  
    /** 
     * ͨ��byte����ȡ��float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static float getFloat(byte[] b, int index) {  
        int l;  
        l = b[index + 0];  
        l &= 0xff;  
        l |= ((long) b[index + 1] << 8);  
        l &= 0xffff;  
        l |= ((long) b[index + 2] << 16);  
        l &= 0xffffff;  
        l |= ((long) b[index + 3] << 24);  
        return Float.intBitsToFloat(l);  
    }  
  
    /** 
     * doubleת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putDouble(byte[] bb, int index, double x) {  
        // byte[] b = new byte[8];  
        long l = Double.doubleToLongBits(x);  
        for (int i = 0; i < 4; i++) {  
            bb[index + i] = new Long(l).byteValue();  
            l = l >> 8;  
        }  
    }  
  
    /** 
     * ͨ��byte����ȡ��float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static double getDouble(byte[] b, int index) {  
        long l;  
        l = b[0];  
        l &= 0xff;  
        l |= ((long) b[1] << 8);  
        l &= 0xffff;  
        l |= ((long) b[2] << 16);  
        l &= 0xffffff;  
        l |= ((long) b[3] << 24);  
        l &= 0xffffffffl;  
        l |= ((long) b[4] << 32);  
        l &= 0xffffffffffl;  
        l |= ((long) b[5] << 40);  
        l &= 0xffffffffffffl;  
        l |= ((long) b[6] << 48);  
        l &= 0xffffffffffffffl;  
        l |= ((long) b[7] << 56);  
        return Double.longBitsToDouble(l);  
    }  
	 

    /** 
     * boolת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putBoolean(byte[] bb, int index, boolean x) {  
 
        bb[index] = (byte)(x ? 1 : 0);
    }  
  
    /** 
     * ͨ��byte����ȡ��bool 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static boolean getBoolean(byte[] b, int index) {  
        
        return 1 == b[index];  
	}
    
    
    /** 
     * stringת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putString(byte[] bb, int index, String x) {  
    	
    	for(byte v : x.getBytes()){
    		bb[index ++ ] = v;
    	}
    }  
  
    /** 
     * ͨ��byte����ȡ��string 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static String getString(byte[] b, int index, int size) {  
        
    	byte[] bytes = new byte[size];
    	for(int i = 0;i < size; i ++){
    		bytes[i] = b[index + i];
    	}
        return new String(bytes);  
	}
    
    /** 
     * bytesת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putBytes(byte[] bb, int index, byte x, int len) {  
    	
    	for(int i = 0;i < len; i ++){
    		bb[index ++] = x;
    	}
    }  
    
    /** 
     * bytesת��byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putBytes(byte[] bb, int index, byte[] x) {  
    	
    	for(byte v : x){
    		bb[index ++ ] = v;
    	}
    }  
  
    /** 
     * ͨ��byte����ȡ��string 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static byte[] getBytes(byte[] b, int index, int size) {  
        
    	byte[] bytes = new byte[size];
    	for(int i = 0;i < size; i ++){
    		bytes[i] = b[index + i];
    	}
        return bytes;  
	}
    
    
   
}
