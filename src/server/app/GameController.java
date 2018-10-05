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
import game.controller.GameManagerPool;
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

	@RequestMapping(method = RequestMethod.GET, value = "/game")
	@ResponseBody
	public ResponseEntity playerInput(@RequestParam(value = "id", defaultValue = "1") int id,
			@RequestParam(value = "choice", defaultValue = "-1") int index) {
		GameManager og = GameManagerPool.getGameManager(id);
		if (og == null) {
			return new ResponseEntity<>("Incorrect game id " + id, HttpStatus.NOT_FOUND);
		}

		if (index == -1) {
//			System.out.println("Request Recieved with gameID");
			return new ResponseEntity<>(og.getGameState().toRestricted(), HttpStatus.OK);

		} else {
//			System.out.println("Request Recieved with choice");
			int size = og.getGameState().getChoices().size();
			if (index >= 0 && index < size) {
				GameManagerPool.getGameManager(id).getGameState().resume(index);
				return new ResponseEntity<>(og.getGameState().toRestricted(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid choice " + index + " for " + size + " options",
						HttpStatus.BAD_REQUEST);
			}
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
