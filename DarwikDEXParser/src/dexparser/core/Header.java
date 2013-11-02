package dexparser.core;

import java.nio.ByteBuffer;

public class Header {

	public static final int ENDIAN_CONSTANT         = 0x12345678;
	public static final int REVERSE_ENDIAN_CONSTANT = 0x78563412;
	public static final int SIGNATURE_UBYTE_SIZE    = 20;
	public final static int MAGIC_LENGTH            = 8;

	private byte[] data;
	public final static String DEX_FILE_MAGIC       =  "dex\n035\0"; // 8
	private int checksum;// 4
	private byte[] signature                        = new byte[SIGNATURE_UBYTE_SIZE];// 20
	private int file_size;// 4
	protected static int header_size;// 4 
	public static int endian_tag;// 4
	private int link_size;// 4
	private int link_off; // 4
	private int map_off; // 4
	private int string_ids_size; // 4
	private int string_ids_off; // 4
	private int type_ids_size; // 4
	private int type_ids_off; // 4
	private int proto_ids_size; // 4
	private int proto_ids_off; // 4
	private int field_ids_size; // 4
	private int field_ids_off; // 4
	private int methhod_ids_size; // 4
	private int method_ids_off; // 4
	private int class_defs_size; // 4
	private int class_defs_off; // 4
	private int data_size; // 4
	private int data_off; // 4
	private byte[] extraBytes;// ?

	public Header(byte[] data) {
		this.data = data;
		this.populateFields();
	}

	protected static void setEndian(byte[] data) {
		int endian_tag_offset = MAGIC_LENGTH + Types.uint_size + SIGNATURE_UBYTE_SIZE +
				Types.uint_size + Types.uint_size;
		byte[] aux = new byte[Types.uint_size];
		System.arraycopy(data, endian_tag_offset, aux, 0, Types.uint_size);
		endian_tag = ByteBuffer.wrap(aux).getInt();
	}
	
	protected static void setHeader_size(byte[] buffer) {
		
		byte header_size_offset = MAGIC_LENGTH + Types.uint_size + SIGNATURE_UBYTE_SIZE +
				Types.uint_size;
		
		int header_size_int = ByteBuffer.wrap(buffer,
				header_size_offset, Types.uint_size).getInt();
		
		if(endian_tag == ENDIAN_CONSTANT) {
			header_size = ByteBuffer.allocate(4).putInt(header_size_int).array()[3];// 0 x 00 00 00 70
		} else {
			header_size = ByteBuffer.allocate(4).putInt(header_size_int).array()[0];// 0 x 70 00 00 00
		}
	}

	private void populateFields() {
		int offset       = MAGIC_LENGTH;
	
		checksum         = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		System.arraycopy(data, offset, signature, 0, SIGNATURE_UBYTE_SIZE);
		offset          += SIGNATURE_UBYTE_SIZE;
		
		file_size        = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		offset          += Types.uint_size; // header_size
		
		offset          += Types.uint_size; // endian_tag field
		
		link_size        = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		link_off         = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		map_off          = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		string_ids_size  = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		string_ids_off   = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		type_ids_size    = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		type_ids_off     = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;	
		
		proto_ids_size   = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		proto_ids_off    = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		field_ids_size   = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		field_ids_off    = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		methhod_ids_size = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		method_ids_off   = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		class_defs_size  = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		class_defs_off   = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		data_size        = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		data_off         = Types.atributeField(data, Types.uint_size, offset);
		offset          += Types.uint_size;
		
		extraBytes       = new byte[header_size - offset];
		System.arraycopy(data, offset, extraBytes, 0, header_size - offset);

	}
	

	
	// metoda asta va fi probabil in tool
	public static String getEndianess() { 
		
		if(endian_tag == ENDIAN_CONSTANT) {
			return "Little Endian ; Raw bytes {" + Integer.toHexString(endian_tag) + "}";
		}
		if(endian_tag == REVERSE_ENDIAN_CONSTANT) {
			return "Big Endian ; Raw bytes {" + Integer.toHexString(endian_tag) + "}";
		} else {
			return "Unknow format";
		}
		
	}
	
	public int getEndian_tag() {
		return endian_tag;
	}
	
	public byte[] getData() {
		return data;
	}

	public String getStringData() {
		return new String(data);
	}
	
	public String getMagic() {
		return DEX_FILE_MAGIC;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getFile_size() {
		return file_size;
	}

	public int getLink_size() {
		return link_size;
	}

	public int getLink_off() {
		return link_off;
	}

	public int getMap_off() {
		return map_off;
	}

	public int getString_ids_size() {
		return string_ids_size;
	}

	public int getString_ids_off() {
		return string_ids_off;
	}

	public int getType_ids_size() {
		return type_ids_size;
	}

	public int getType_ids_off() {
		return type_ids_off;
	}

	public int getProto_ids_size() {
		return proto_ids_size;
	}

	public int getProto_ids_off() {
		return proto_ids_off;
	}

	public int getField_ids_size() {
		return field_ids_size;
	}

	public int getField_ids_off() {
		return field_ids_off;
	}

	public int getMethod_ids_off() {
		return method_ids_off;
	}

	public int getMethhod_ids_size() {
		return methhod_ids_size;
	}

	public int getClass_defs_size() {
		return class_defs_size;
	}

	public int getClass_defs_off() {
		return class_defs_off;
	}

	public int getData_size() {
		return data_size;
	}

	public int getData_off() {
		return data_off;
	}

	public byte[] getExtraBytes() {
		return extraBytes;
	}

}
