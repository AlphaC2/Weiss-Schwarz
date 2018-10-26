package game.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleReader extends Reader{
	private BufferedReader r;
	
	public ConsoleReader() {
		super();
		r = new BufferedReader(new InputStreamReader(System.in));
	}
	
	
	public <T> T getChoice(String prompt, List<T> choices){
		if (choices.size() == 1){
			return choices.get(0);
		}
		
		
		while(true){
			if(pc.getGM().getThread().isInterrupted()){
				System.out.println("DIE");
			}
			System.out.println();
			System.out.println(prompt);
			for (int i = 0; i < choices.size(); i++){
				Object o = choices.get(i);
				System.out.println(i + ". " + o);
			}
			System.out.println("Enter choice: ");
			try {
				String choice =  r.readLine();
				int opt = Integer.parseInt(choice);
				if (opt >= 0 && opt < choices.size()){
					System.out.println();
					return choices.get(opt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch(IllegalArgumentException e){
				
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

//	@Override
//	public void buildDeck() {
//		// TODO Auto-generated method stub
//	}
	
}
