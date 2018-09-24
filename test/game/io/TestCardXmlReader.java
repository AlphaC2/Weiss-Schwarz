package game.io;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import game.model.card.Card;
import game.model.card.CardFactory;
import game.model.card.Character;
import game.model.card.Colour;
import game.model.card.Rarity;
import game.model.card.Trigger;

public class TestCardXmlReader {

	@Test
	public void testCharacter(){
		Character c = (Character) CardFactory.createCard("GC-S16-001");
		assertEquals("GC-S16-001",c.getCardID());
		assertEquals("リーダーの風格",c.getName());
		assertEquals(Rarity.RR, c.getRarity());
		assertEquals("https://yuyu-tei.jp/card_image/ws/front/gc/10029.jpg",c.getImagePath());
		assertEquals(Colour.YELLOW,c.getColour());
		assertEquals(1,c.getLevel());
		assertEquals(0,c.getCost());
		List<Trigger> triggers = new ArrayList<>();
		triggers.add(Trigger.NONE);
		assertEquals(triggers,c.getTrigger());
		assertEquals("まずは生き延びろ。全てはそれからだ",c.getFlavourText());
		assertEquals(4500,c.getPower());
		assertTrue(c.getTraits().contains("FUNERAL_PARLOR"));
		assertTrue(c.getTraits().contains("WEAPON"));
		assertEquals(1,c.getSoul());
	}
	
	@Test
	public void testSet(){
		Set<Card> set = CardXMLReader.readSet("GC-S16");
		assertEquals(146, set.size());
	}
}
