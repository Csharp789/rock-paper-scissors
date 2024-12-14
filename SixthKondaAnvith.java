import java.util.Random;

public class SixthKondaAnvith implements Player {
    private static String name = "KondaAnvith";
    private Random random = new Random();

    public String move(String[] myMoves, String[] opponentMoves, int myScore, int opponentScore) {
        if (myMoves.length == 0) {
            return randomMove();
        }

        String opponentPrediction = analyzeOpponent(opponentMoves);
        return decideStrategy(opponentPrediction, opponentMoves);
    }

    private String analyzeOpponent(String[] moves) {
        if (moves == null || moves.length == 0) {
            return randomMove();
        }
        int[] frequencies = tallyMoves(moves);
        return predictNextMove(frequencies);
    }

    private int[] tallyMoves(String[] moves) {
        int[] counts = {0, 0, 0};
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] == null) continue;
            int weight = moves.length - i; 
            switch (moves[i]) {
                case "r":
                    counts[0] += weight;
                    break;
                case "p":
                    counts[1] += weight;
                    break;
                case "s":
                    counts[2] += weight;
                    break;
            }
        }
        return counts;
    }

    private String predictNextMove(int[] counts) {
        int highest = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[highest]) {
                highest = i;
            }
        }
        return highest == 0 ? "r" : highest == 1 ? "p" : "s";
    }

    private String randomMove() {
        int randomChoice = random.nextInt(3);
        return randomChoice == 0 ? "r" : randomChoice == 1 ? "p" : "s";
    }

    private String randomizeCounter(String predictedMove) {
        double chance = random.nextDouble();
        if (chance < 0.33) {
            return randomMove();
        }
        switch (predictedMove) {
            case "r":
                return "p";
            case "p":
                return "s";
            default:
                return "r";
        }
    }

    private String decideStrategy(String predictedMove, String[] opponentMoves) {
        int strategy = random.nextInt(3);
        switch (strategy) {
            case 0:
                return randomMove(); 
            case 1:
                return randomizeCounter(predictedMove); 
            case 2:
                return mirrorOpponent(opponentMoves);
            default:
                return randomMove();
        }
    }

    private String mirrorOpponent(String[] opponentMoves) {
        if (opponentMoves.length == 0) return randomMove();
        return opponentMoves[opponentMoves.length - 1];
    }

    public String getName() {
        return name;
    }
}
