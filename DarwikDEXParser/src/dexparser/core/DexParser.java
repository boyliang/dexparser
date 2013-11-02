package dexparser.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import exceptions.FileNotDexException;

public class DexParser {

	private File dexFile = null;
	private static final int AUXILIRY_BUFFER_SIZE = 4096;
	int header_size_offset;

	private Header header;

	// celelalte componente vor continua astfel

	public DexParser(File dexFile) throws FileNotDexException {
		if (validateDexFile(dexFile)) {
			this.dexFile = dexFile;
			this.setHeader_size_offset();
			this.readData();

		} else
			throw new FileNotDexException();
	}

	private boolean validateDexFile(File dexFile) {
		byte[] magic = new byte[Header.MAGIC_LENGTH];
		int intValueOfChar = 0;
		int count = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(dexFile));

			while (count < magic.length && (intValueOfChar = in.read()) != -1) {
				magic[count++] = (byte) intValueOfChar;
			}
			in.close();

			if (count != Header.MAGIC_LENGTH) {
				return false; // aici ar trebui cu diferentele de cod din
								// exception
			}

			// sa fie gen "dex" + "\n" + "[0-9]+" + "\0"
			// sau comparare cu magic standard?

			if (!Header.DEX_FILE_MAGIC.equals(new String(magic))) {
				return false;
			}

		} catch (IOException e) {
			// further information
		}

		return true;
	}

	private void setHeader_size_offset() {
		header_size_offset = Header.MAGIC_LENGTH + Types.uint_size
				+ Header.SIGNATURE_UBYTE_SIZE + Types.uint_size
				+ Types.uint_size + Types.uint_size; // si 4 pt el si 4 pt
														// endineess
	}

	private boolean readData() {
		try {

			BufferedReader in = new BufferedReader(new FileReader(dexFile));

			byte[] buffer = new byte[AUXILIRY_BUFFER_SIZE];
			int intValueOfChar = 0;
			int count = 0;

			while ((intValueOfChar = in.read()) != -1) {
				if (count == header_size_offset) {		
					break;
				}

				buffer[count++] = (byte) intValueOfChar;
			}
			
			Header.setEndian(buffer);
			Header.setHeader_size(buffer);

			while (count < Header.header_size && (intValueOfChar = in.read()) != -1) {
				buffer[count++] = (byte) intValueOfChar;
			}
	
			header = new Header(buffer);
			
			// now read string_id bytes
			//string_id = new ... etc
			// this is my logic

			in.close();
		} catch (IOException e) {
		}

		return false;
	}

	public Header getHeader() {
		return header;
	}
}
