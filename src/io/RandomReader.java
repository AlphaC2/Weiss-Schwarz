package io;

import java.util.List;
import java.util.Random;

import controller.ReadUserInput;

public class RandomReader implements ReadUserInput  {

	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		int max = choices.size();
		Random r = new Random();
		return choices.get(r.nextInt(max));
	}

	@Override
	public boolean getChoice(String prompt) {
		Random r = new Random();
		return r.nextBoolean();
	}

}
