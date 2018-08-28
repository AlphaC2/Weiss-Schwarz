package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadUserInput {
	private BufferedReader r;
	
	public ReadUserInput() {
		super();
		r = new BufferedReader(new InputStreamReader(System.in));
	}


	public int getInt(){
		System.out.println("Enter a number:");
		while(true){
			try {
				return Integer.parseInt(r.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("Not a number");
			}
		}
	}
	
	public String getLine(){
		System.out.println("Enter a command");
		while(true){
			try {
				return r.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
