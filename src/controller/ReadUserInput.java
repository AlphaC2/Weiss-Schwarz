package controller;

import java.util.List;

public interface ReadUserInput {
	public <T> T getChoice(String prompt, List<T> choices);
	public boolean getChoice(String prompt);
}
