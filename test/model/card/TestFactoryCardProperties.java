package model.card;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TestFactoryCardProperties {
	
	@Test
	public void checkForTypos(){
		Arrays.asList(DummyName.values()).forEach(name -> assertNotNull(DummyFactory.createCard(name)));
	}
}
