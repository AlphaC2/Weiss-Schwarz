package io;

import static org.junit.Assert.*;

import org.junit.Test;

import model.card.Card;

public class TestXMLReader {
	
	@Test
	public void testReadCard(){
		CardXMLReader reader = new CardXMLReader();
		Card c = reader.read("CardData\\AB\\W11\\AB-W11-101.xml");
		Card cc = reader.read("CardData\\AB\\W11\\AB-W11-T07.xml");
		System.out.println(c);
		System.out.println(cc);
		assertNotNull(c);
		assertNotNull(cc);
	}

}
