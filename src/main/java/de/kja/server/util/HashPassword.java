package de.kja.server.util;

import java.io.Console;

public class HashPassword {
	
	public static void main(String[] args) {
		Console console = System.console();
		char[] plain = console.readPassword("Enter password to hash: ");
		PasswordAuthentication authentication = new PasswordAuthentication();
		String hash = authentication.hash(plain);
		console.writer().println(hash);
	}

}
