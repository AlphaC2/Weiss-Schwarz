package server.app;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import game.controller.GameManager;
import game.controller.GameManagerPool;
import game.controller.GameState;
import game.io.CardXMLReader;
import game.model.card.Card;
import game.model.card.CardFactory;

@RestController
@SuppressWarnings("rawtypes")
public class GameController {
	@Value("${spring.application.name}")
	private String name;
	
	@RequestMapping(method = RequestMethod.GET, value = "/name")
	public String getName() {
		return name;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/series")
	public Set<Card> getSeries(@RequestParam(value = "id", defaultValue = "GC-16") String id) {
		Set<Card> set = CardXMLReader.readSet(id);
		return set;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/card")
	public Card getCard(@RequestParam(value = "id", defaultValue = "GC-S16-001") String id) {
		Card card = CardFactory.createCard(id);
		return card;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/newGame")
	public ResponseEntity createGame() {
		int gameID = GameManagerPool.createGameManager();
		if (gameID == -1) {
			return new ResponseEntity<>("Max Allowed Games Already Running", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("New Game Created, ID=" + gameID, HttpStatus.OK);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/games")
	public ResponseEntity getGameIDs() {
		return new ResponseEntity<>(GameManagerPool.getGameIDs(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/game")
	public ResponseEntity getGameState(@RequestParam(value = "id", defaultValue = "1") int id) {
		GameManager gm = GameManagerPool.getGameManager(id);
		if ( gm == null){
			return new ResponseEntity<>("Incorrect game id " + id,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(gm.getGameState().toRestricted(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	@ResponseBody
	public ResponseEntity playerInput(@RequestParam(value = "id", defaultValue = "1") int id,
			@RequestParam(value = "choice", defaultValue = "true") int index) {
		
		GameManager gm = GameManagerPool.getGameManager(id);
		if (gm == null){
			return new ResponseEntity<>("Incorrect game id " + id, HttpStatus.NOT_FOUND);
		}
		GameState gameState = gm.getGameState();
		int size = gameState.getChoices().size();
		if (index >= 0 && index < size){
			gameState.resume(index);
			return new ResponseEntity<>(gameState.toRestricted(),HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid choice " + index + " for " + size + " options",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/endGame")
	public ResponseEntity endGame(@RequestParam(value = "id", defaultValue = "1") int id) {
		boolean success = GameManagerPool.endGame(id);
		if (!success) {
			return new ResponseEntity<>("Incorrect game id " + id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Game Ended, ID=" + id, HttpStatus.OK);
		}
	}

}
