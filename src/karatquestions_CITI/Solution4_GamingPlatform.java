package karatquestions_CITI;

import java.util.*;

enum Outcome {
	WIN, LOSS, DRAW
}

class Player {
	public int playerId;
	public String username;

	public Player(int playerId, String username) {
		this.playerId = playerId;
		this.username = username;
	}
}

class HeadToHead {
	int winsPlayer1;
	int winsPlayer2;
	int draws;
	int totalMatches;
	Outcome lastResult;
	Integer lastMatchTimestamp;

	HeadToHead(int winsPlayer1, int winsPlayer2, int draws, int totalMatches, Outcome lastResult,
			Integer lastMatchTimestamp) {
		this.winsPlayer1 = winsPlayer1;
		this.winsPlayer2 = winsPlayer2;
		this.draws = draws;
		this.totalMatches = totalMatches;
		this.lastResult = lastResult;
		this.lastMatchTimestamp = lastMatchTimestamp;
	}
}

class MatchResult {
	public int playerId;
	public int opponentId;
	public Outcome outcome;
	public int score;
	public int timestamp;

	public MatchResult(int playerId, int opponentId, Outcome outcome, int score, int timestamp) {
		this.playerId = playerId;
		this.opponentId = opponentId;
		this.outcome = outcome;
		this.score = score;
		this.timestamp = timestamp;
	}
}

class PlayerStats {
	public int totalMatches;
	public int wins;
	public double winRate;

	public PlayerStats(int totalMatches, int wins, double winRate) {
		this.totalMatches = totalMatches;
		this.wins = wins;
		this.winRate = winRate;
	}
}

class GameManager {
	public Map<Integer, Player> players;
	public List<MatchResult> matchResults;

	public GameManager() {
		players = new HashMap<>();
		matchResults = new ArrayList<>();
	}

	public void addPlayer(Player player) {
		players.put(player.playerId, player);
	}

	public void addMatchResult(MatchResult matchResult) {

		if (matchResult == null) {
			return;
		}
		if (!players.containsKey(matchResult.playerId)) {
			return;
		}
		matchResults.add(matchResult);
	}

	public Map<Outcome, Double> getAverageScoreByOutcome(int playerId) {

		Map<Outcome, Integer> totalScoreByOutcome = new EnumMap<>(Outcome.class);

		Map<Outcome, Integer> resultCountByOutcome = new EnumMap<>(Outcome.class);

		for (MatchResult matchResult : matchResults) {

			if (matchResult.playerId != playerId) {
				continue;
			}

			Outcome outcome = matchResult.outcome;

			int existingTotal = totalScoreByOutcome.getOrDefault(outcome, 0);

			totalScoreByOutcome.put(outcome, existingTotal + matchResult.score);

			int existingCount = resultCountByOutcome.getOrDefault(outcome, 0);

			resultCountByOutcome.put(outcome, existingCount + 1);
		}

		Map<Outcome, Double> averageScoreByOutcome = new EnumMap<>(Outcome.class);

		for (Outcome outcome : totalScoreByOutcome.keySet()) {

			int totalScore = totalScoreByOutcome.get(outcome);
			int numberOfResults = resultCountByOutcome.get(outcome);

			double average = (double) totalScore / numberOfResults;

			averageScoreByOutcome.put(outcome, average);
		}

		return averageScoreByOutcome;
	}

	public HeadToHead getHeadToHead(int player1Id, int player2Id) {

		int winsPlayer1 = 0;
		int winsPlayer2 = 0;
		int draws = 0;
		int totalMatches = 0;

		Outcome lastResult = null;
		Integer lastMatchTimestamp = null;

		for (MatchResult matchResult : matchResults) {
			if (matchResult.playerId != player1Id || matchResult.opponentId != player2Id) {
				continue;
			}
			totalMatches++;

			if (matchResult.outcome == Outcome.WIN) {
				winsPlayer1++;
			} else if (matchResult.outcome == Outcome.LOSS) {
				winsPlayer2++;
			} else if (matchResult.outcome == Outcome.DRAW) {
				draws++;
			}

			if (lastMatchTimestamp == null || matchResult.timestamp > lastMatchTimestamp) {

				lastMatchTimestamp = matchResult.timestamp;
				lastResult = matchResult.outcome;
			}
		}

		return new HeadToHead(winsPlayer1, winsPlayer2, draws, totalMatches, lastResult, lastMatchTimestamp);
	}

	public PlayerStats getPlayerStatistics(int playerId) {
		List<MatchResult> playerMatches = new ArrayList<>();
		for (MatchResult m : matchResults) {
			if (m.playerId == playerId) {
				playerMatches.add(m);
			}
		}

		int totalMatches = 0;
		for (MatchResult m : playerMatches) {
			if (m.outcome == Outcome.WIN || m.outcome == Outcome.LOSS) {
				totalMatches++;
			}
		}

		int wins = 0;
		for (MatchResult m : playerMatches) {
			if (m.outcome == Outcome.WIN) {
				wins++;
			}
		}

		double winRate;
		if (totalMatches > 0) {
			winRate = (double) wins / totalMatches;
		} else {
			winRate = 0.0;
		}

		return new PlayerStats(totalMatches, wins, winRate);
	}

}

public class Solution4_GamingPlatform {
	public static void main(String[] args) {
		testGetPlayerStatistics();
		testAddMatchResult();
		testGetAverageScoreByOutcome();
		// testGetHeadToHead();
		System.out.println("All Tests Passed!");
	}

	public static void testGetPlayerStatistics() {
		System.out.println("Running testGetPlayerStatistics");
		GameManager gm = new GameManager();
		gm.addPlayer(new Player(1, "player1"));
		gm.addPlayer(new Player(2, "player2"));

		gm.matchResults.add(new MatchResult(1, 2, Outcome.WIN, 80, 1000));
		gm.matchResults.add(new MatchResult(1, 2, Outcome.LOSS, 50, 2000));
		gm.matchResults.add(new MatchResult(1, 2, Outcome.DRAW, 60, 3000));
		gm.matchResults.add(new MatchResult(1, 2, Outcome.WIN, 90, 4000));

		PlayerStats stats = gm.getPlayerStatistics(1);
		assert stats.totalMatches == 4 : "totalMatches should be 4, was " + stats.totalMatches;
		assert stats.wins == 2 : "wins should be 2, was " + stats.wins;
		assert Math.abs(stats.winRate - 0.5) < 1e-4 : "winRate should be 0.5, was " + stats.winRate;

		gm.matchResults.add(new MatchResult(2, 1, Outcome.DRAW, 60, 1000));
		gm.matchResults.add(new MatchResult(2, 1, Outcome.DRAW, 60, 2000));

		PlayerStats stats2 = gm.getPlayerStatistics(2);
		assert stats2.totalMatches == 2 : "totalMatches should be 2, was " + stats2.totalMatches;
		assert stats2.wins == 0 : "wins should be 0, was " + stats2.wins;
		assert Math.abs(stats2.winRate - 0.0) < 1e-4 : "winRate should be 0.0, was " + stats2.winRate;
	}

	static void testAddMatchResult() {
		System.out.println("Running testAddMatchResult");
		GameManager gm = new GameManager();
		gm.addPlayer(new Player(1, "player1"));
		gm.addPlayer(new Player(2, "player2"));

		gm.addMatchResult(new MatchResult(1, 2, Outcome.WIN, 80, 1000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.LOSS, 50, 1000));

		// unknown player ignored
		gm.addMatchResult(new MatchResult(99, 1, Outcome.WIN, 100, 2000));

		assert gm.matchResults.size() == 2 : "Expected 2 match results";
	}

	static void testGetAverageScoreByOutcome() {
		System.out.println("Running testGetAverageScoreByOutcome");
		GameManager gm = new GameManager();
		gm.addPlayer(new Player(1, "player1"));
		gm.addPlayer(new Player(2, "player2"));

		// match 1 - player1 wins
		gm.addMatchResult(new MatchResult(1, 2, Outcome.WIN, 80, 1000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.LOSS, 50, 1000));

		// match 2 - player1 wins again
		gm.addMatchResult(new MatchResult(1, 2, Outcome.WIN, 91, 2000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.LOSS, 60, 2000));

		// match 3 - draw
		gm.addMatchResult(new MatchResult(1, 2, Outcome.DRAW, 70, 3000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.DRAW, 70, 3000));

		Map<Outcome, Double> avg1 = gm.getAverageScoreByOutcome(1);
		assert Math.abs(85.5 - avg1.get(Outcome.WIN)) < 1e-4 : "Expected 85.5 for WIN"; // (80+91)/2
		assert Math.abs(70.0 - avg1.get(Outcome.DRAW)) < 1e-4 : "Expected 70.0 for DRAW";
		assert !avg1.containsKey(Outcome.LOSS) : "player1 has no losses"; // player1 has no losses

		Map<Outcome, Double> avg2 = gm.getAverageScoreByOutcome(2);
		assert Math.abs(55.0 - avg2.get(Outcome.LOSS)) < 1e-4 : "Expected 55.0 for LOSS"; // (50+60)/2
		assert Math.abs(70.0 - avg2.get(Outcome.DRAW)) < 1e-4 : "Expected 70.0 for DRAW";
		assert !avg2.containsKey(Outcome.WIN) : "player2 has no wins"; // player2 has no wins

		// player with no match results
		gm.addPlayer(new Player(3, "player3"));
		assert gm.getAverageScoreByOutcome(3).isEmpty() : "Expected empty map for player3";
	}

	static void testGetHeadToHead() {
		System.out.println("Running testGetHeadToHead");
		GameManager gm = new GameManager();
		gm.addPlayer(new Player(1, "player1"));
		gm.addPlayer(new Player(2, "player2"));
		gm.addPlayer(new Player(3, "player3"));

		// match 4 - player1 wins (most recent)
		gm.addMatchResult(new MatchResult(1, 2, Outcome.WIN, 85, 4000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.LOSS, 65, 4000));

		// match 1 - player1 wins
		gm.addMatchResult(new MatchResult(1, 2, Outcome.WIN, 80, 1000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.LOSS, 50, 1000));

		// match 2 - player2 wins
		gm.addMatchResult(new MatchResult(1, 2, Outcome.LOSS, 60, 2000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.WIN, 90, 2000));

		// match 3 - draw
		gm.addMatchResult(new MatchResult(1, 2, Outcome.DRAW, 70, 3000));
		gm.addMatchResult(new MatchResult(2, 1, Outcome.DRAW, 70, 3000));

		HeadToHead h2h = gm.getHeadToHead(1, 2);
		assert h2h.winsPlayer1 == 2 : "Expected 2"; // match 1 + match 4
		assert h2h.winsPlayer2 == 1 : "Expected 1"; // match 2 only
		assert h2h.draws == 1 : "Expected 1"; // match 3 only
		assert h2h.totalMatches == 4 : "Expected 4"; // all 4 matches
		assert h2h.lastResult == Outcome.WIN : "Expected WIN"; // match 4 was a WIN for player1
		assert h2h.lastMatchTimestamp == 4000 : "Expected 4000";

		// from player2's perspective
		HeadToHead h2hReverse = gm.getHeadToHead(2, 1);
		assert h2hReverse.winsPlayer1 == 1 : "Expected 1"; // player2 won match 2
		assert h2hReverse.winsPlayer2 == 2 : "Expected 2"; // player1 won match 1 + 4
		assert h2hReverse.draws == 1 : "Expected 1";
		assert h2hReverse.totalMatches == 4 : "Expected 4";
		assert h2hReverse.lastResult == Outcome.LOSS : "Expected LOSS"; // match 4 was a LOSS for player2
		assert h2hReverse.lastMatchTimestamp == 4000 : "Expected 4000";

		// players who have never faced each other — unchanged
		HeadToHead h2hEmpty = gm.getHeadToHead(1, 3);
		assert h2hEmpty.totalMatches == 0 : "Expected 0";
		assert h2hEmpty.winsPlayer1 == 0 : "Expected 0";
		assert h2hEmpty.winsPlayer2 == 0 : "Expected 0";
		assert h2hEmpty.draws == 0 : "Expected 0";
		assert h2hEmpty.lastResult == null : "Expected null";
		assert h2hEmpty.lastMatchTimestamp == null : "Expected null";
	}

}