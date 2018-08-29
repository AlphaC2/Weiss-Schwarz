package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import controller.ReadUserInput;

public class ConsoleReadUserInput implements ReadUserInput{
	private BufferedReader r;
	
	public ConsoleReadUserInput() {
		super();
		r = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public <T> T getChoice(String prompt, List<T> choices){
		while(true){
			System.out.println(prompt);
			for (int i = 0; i < choices.size(); i++){
				Object o = choices.get(i);
				System.out.println(i + ". " + o.toString());
			}
			System.out.println("Enter choice: ");
			try {
				String choice =  r.readLine();
				int opt = Integer.parseInt(choice);
				if (opt >= 0 && opt < choices.size()){
					return choices.get(opt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean getChoice(String prompt) {
		System.out.println(prompt + "(y/n)");
		try {
			String choice =  r.readLine();
			if (choice.toLowerCase().startsWith("y")){
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
