package server.app;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import game.controller.GameManager;
import game.controller.GameState;
import game.io.CardXMLReader;
import game.model.card.Card;
import game.model.card.CardFactory;

@RestController
public class GameController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/series")
	public Set<Card> getSeries(@RequestParam(value = "id", defaultValue = "GC-16") String id) {
		return CardXMLReader.readSet(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/card")
	public Card getCard(@RequestParam(value = "id", defaultValue = "GC-S16-001") String id) {
		return CardFactory.createCard(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/game")
	public GameState getGameState(@RequestParam(value = "id", defaultValue = "1") int id) {
		GameState og = GameManager.getGameState(id);
		return new GameState(og.getP1(), og.getP2().toRestricted());
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	public void playerInput(@RequestParam(value = "id", defaultValue = "1") int id,
			@RequestParam(value = "choice", defaultValue = "true") int index) {
		GameManager.getGameState(id).resume(index);
	}
	
}
