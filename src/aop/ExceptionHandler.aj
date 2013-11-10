package com.remmylife.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public aspect ExceptionHandler {
	private String filename = "log/exceptions.txt";
	
	before(Exception e) : handler(Exception+) && args(e) {
		String output = "/**\n"
				      + " * Exception : " + thisJoinPoint.getStaticPart().getSignature() + "\n"
				      + " * Location  : " + thisJoinPoint.getStaticPart().getSourceLocation() + "\n"
				      + " */\n\n";
		writeFile(filename, output);
	}
	
	private void writeFile(String file, String text)  { 
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(text);
			printWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
