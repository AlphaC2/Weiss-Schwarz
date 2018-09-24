package server.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import game.model.card.Card;
import game.model.card.CardFactory;


@RestController
public class GameController {
	
	 @RequestMapping(method= RequestMethod.GET, value ="/card")
	 public Card getCard(@RequestParam(value="id", defaultValue="GC-S16-001") String id) {
	        return CardFactory.createCard(id);
	    }
}
