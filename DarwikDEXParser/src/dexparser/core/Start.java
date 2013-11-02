package dexparser.core;

import java.io.File;


import exceptions.FileNotDexException;

public class Start {
	public static void main(String[] args) {
		
		String path = "A:\\Alin\\Facultate\\Proiecte\\Proiect RC\\DEX\\DEX exemples\\classes_default.dex";
		File dexFile = new File(path);
		try {
			DexParser parser = new DexParser(dexFile);
			System.out.println(parser.getHeader().getStringData());
			Header header = parser.getHeader();
			//System.out.println(header.getMagic());
			//System.out.println(header.getFile_size());
			
			//byte[] x = Types.getBytesAsArray(header.getEndian_tag());
			
			System.out.println(header.getExtraBytes().length);
			
		} catch (FileNotDexException e) {
			e.printStackTrace();
		}
		
	}
}
