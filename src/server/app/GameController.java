package server.app;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import game.io.CardXMLReader;
import game.model.card.Card;
import game.model.card.CardFactory;

@RestController
public class GameController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/series")
	public Set<Card> getSeries(@RequestParam(value = "id", defaultValue = "GC") String id) {
		return CardXMLReader.readSet(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/card")
	public Card getCard(@RequestParam(value = "id", defaultValue = "GC-S16-001") String id) {
		return CardFactory.createCard(id);
	}
}
