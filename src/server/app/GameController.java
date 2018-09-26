package server.app;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import game.controller.GameManager;
import game.controller.GameState;
import game.io.CardXMLReader;
import game.model.card.Card;
import game.model.card.CardFactory;

@RestController
@SuppressWarnings("rawtypes")
public class GameController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/series")
	public Set<Card> getSeries(@RequestParam(value = "id", defaultValue = "GC-16") String id) {
		Set<Card> set = CardXMLReader.readSet(id);
		return set;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/card")
	public Card getCard(@RequestParam(value = "id", defaultValue = "GC-S16-001") String id) {
		return CardFactory.createCard(id);
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/game")
	public ResponseEntity getGameState(@RequestParam(value = "id", defaultValue = "1") int id) {
		GameState og = GameManager.getGameState(id);
		if ( og == null){
			return new ResponseEntity<>("Incorrect game id " + id,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(og.toRestricted(),HttpStatus.OK);
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	@ResponseBody
	public ResponseEntity playerInput(@RequestParam(value = "id", defaultValue = "1") int id,
			@RequestParam(value = "choice", defaultValue = "true") int index) {
		GameState og = GameManager.getGameState(id);
		if (og == null){
			return new ResponseEntity<>("Incorrect game id " + id,HttpStatus.NOT_FOUND);
		}
		int size = GameManager.getGameState(id).getChoices().size();
		if (index >= 0 && index < size){
			GameManager.getGameState(id).resume(index);
			return new ResponseEntity<>(og.toRestricted(),HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid choice " + index + " for " + size + " options",HttpStatus.BAD_REQUEST);
		}
	}
	
}
