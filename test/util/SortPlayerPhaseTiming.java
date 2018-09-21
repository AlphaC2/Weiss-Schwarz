package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.player.PhaseTiming;
import model.player.Player;
import model.player.PlayerPhase;
import model.player.PlayerPhaseTiming;

public class SortPlayerPhaseTiming {
	private List<PlayerPhaseTiming> expected;
	private List<PlayerPhaseTiming> sorted;

	@Before
	public void init() {
		expected = new ArrayList<PlayerPhaseTiming>();

		Arrays.asList(PlayerPhase.values()).forEach(phase -> {
			expected.add(new PlayerPhaseTiming(true, phase, PhaseTiming.START));
			expected.add(new PlayerPhaseTiming(true, phase, PhaseTiming.END));
		});

		Arrays.asList(PlayerPhase.values()).forEach(phase -> {
			expected.add(new PlayerPhaseTiming(false, phase, PhaseTiming.START));
			expected.add(new PlayerPhaseTiming(false, phase, PhaseTiming.END));
		});

		sorted = new ArrayList<PlayerPhaseTiming>();
		sorted.addAll(expected);

		System.out.println("Expected\n");
		for (PlayerPhaseTiming playerPhaseTiming : sorted) {
			System.out.println(playerPhaseTiming);
		}

		Collections.shuffle(sorted);
		Collections.sort(sorted);

		System.out.println("\nSORTED\n");
		for (PlayerPhaseTiming playerPhaseTiming : sorted) {
			System.out.println(playerPhaseTiming);
		}

	}

	@Test
	public void checkOrder() {
		assertEquals(expected.size(), sorted.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), sorted.get(i));
		}
	}

	
	@Test
	public void t1(){
		assertTrue(new PlayerPhaseTiming(PlayerPhase.DRAW, PhaseTiming.END)
				.compareTo(new PlayerPhaseTiming(PlayerPhase.OPPONENTS_TURN, PhaseTiming.END))  > 0 );
	}
}
