package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadUserInput {
	//private Scanner sc;
	private BufferedReader r;
	
	public ReadUserInput() {
		super();
		//sc = new Scanner(System.in);
		r = new BufferedReader(new InputStreamReader(System.in));
	}


	public int getInt(){
		System.out.println("Enter a number:");
		while(true){
			try {
				return Integer.parseInt(r.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//return sc.nextInt();
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
		//return sc.nextLine();
	}
	
}
