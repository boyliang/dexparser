package dexparser.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Types {
	public static final int uint_size = 4;

	public static byte[] getBytesAsArray(int data) {
		return ByteBuffer.allocate(4).putInt(data).array();
	}

	public static byte[] getBytesAsArray(long data) {
		return ByteBuffer.allocate(8).putLong(data).array();
	}

	public static byte[] reverseEndianness(int data) {

		ByteBuffer buffer = ByteBuffer.wrap(getBytesAsArray(data));

		if (ByteOrder.BIG_ENDIAN.equals(buffer.order())) {
			buffer.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			buffer.order(ByteOrder.BIG_ENDIAN);
		}
		return buffer.array();

	}
	
	public static byte[] reverseEndianness(long data) {

		ByteBuffer buffer = ByteBuffer.wrap(getBytesAsArray(data));

		if (ByteOrder.BIG_ENDIAN.equals(buffer.order())) {
			buffer.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			buffer.order(ByteOrder.BIG_ENDIAN);
		}
		return buffer.array();
	}
	
	public static byte[] reverseEndianness(byte[] data) {

		ByteBuffer buffer = ByteBuffer.wrap(data);

		if (ByteOrder.BIG_ENDIAN.equals(buffer.order())) {
			buffer.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			buffer.order(ByteOrder.BIG_ENDIAN);
		}
		return buffer.array();
	}
	
	public static int atributeField(byte[] data,int formatLength, int offset) {
		
		byte[] aux = new byte[uint_size];

		 if(Header.endian_tag == Header.ENDIAN_CONSTANT) {
				
			System.arraycopy(data, offset, aux, 0, formatLength);
			ByteBuffer buffer = ByteBuffer.wrap(aux);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			return buffer.getInt(); // getLong() ?
			
				
		}
		 if(Header.endian_tag == Header.REVERSE_ENDIAN_CONSTANT) {
			 
			System.arraycopy(data, offset, aux, 0, formatLength);
			ByteBuffer buffer = ByteBuffer.wrap(aux);
			buffer.order(ByteOrder.BIG_ENDIAN);
			return buffer.getInt();
		}
		 return 0;	
	}
}
