package ohtu;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class TennisGame {

    private final int maxSpecifiedScore = 3;
    private final String[] scoreStringMap = {
        "Love", "Fifteen", "Thirty", "Forty"
    };

    private int player1Score = 0;
    private int player2Score = 0;
    private final String player1Name;
    private final String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Score++;
        } else if (playerName.equals(player2Name)) {
            player2Score++;
        }
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return equalScoresString();
        } else if (max(player1Score, player2Score) > maxSpecifiedScore) {
            return gamePointOrWinString();
        } else {
            return earlyGameScoresString();
        }
    }

    private String earlyGameScoresString() {
        String output = mapScoreToString(player1Score);
        output += "-" + mapScoreToString(player2Score);

        return output;
    }

    private String equalScoresString() {
        if (player1Score <= maxSpecifiedScore) {
            return mapScoreToString(player1Score) + "-All";
        }

        return "Deuce";
    }

    private String mapScoreToString(int score) {
        return scoreStringMap[score];
    }

    private String gamePointOrWinString() {
        int scoreDifference = player1Score - player2Score;

        String result = getTypeOfAdvantage(scoreDifference);
        result += " " + getPlayerWithAdvantage(scoreDifference);

        return result;
    }

    private String getTypeOfAdvantage(int scoreDifference) {
        if (abs(scoreDifference) == 1) {
            return "Advantage";
        } else {
            return "Win for";
        }
    }

    private String getPlayerWithAdvantage(int scoreDifference) {
        if (scoreDifference > 0) {
            return player1Name;
        } else {
            return player2Name;
        }
    }
}
