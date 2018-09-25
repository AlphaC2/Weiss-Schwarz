package game.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.PayStock;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.board.SlotType;
import game.model.card.Character;
import game.model.card.Position;
import game.model.gameEvent.DamageCancelledEvent;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.StageToWaitingRoomEvent;


public class Encore extends Command{

	public Encore() {
		super("Encore");
	}
	
	private void encore(PlayerController pc, PlayerController po){
		Board board = pc.getBoard();
		List<Slot> deadSlots = board.getStage().getCharacterByPosition(Position.REVERSED);
		List<Character> deadChars = new ArrayList<>();
		for (Slot slot : deadSlots) {
			deadChars.add(slot.getCharacter());
		}
		Iterator<Character> iterator = deadChars.iterator();
		Character current;
		SlotType s;
		while (iterator.hasNext()) {
			current = iterator.next();
			s = board.getStage().getSlot(current).getSlotType();
			board.getWaitingRoom().add(current);
			board.getStage().removeCharacter(s);
			
			List<GameEvent> events = new ArrayList<>();
			events.add(new StageToWaitingRoomEvent(pc.getPlayer(), current));
			pc.addEvents(events,po);
			
			
			boolean choice = pc.getChoice(pc.getPlayer().getName() + " Encore:" + current.toShortString() + "?");
			if (choice) {
				new PayStock(3).execute(pc, po);
				//new Pay
				board.getWaitingRoom().remove(current);
				board.getStage().place(current, s);
				pc.log(current.toShortString() + " encored");
			}
		}
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		encore(p1, p2);
		encore(p2, p2);
	}

}
