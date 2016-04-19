package battle;

import battle.controllers.Human.ArrowsController;
import battle.controllers.Human.WASDController;
import battle.controllers.diego.BattleEvoController;
import battle.controllers.diego.search.*;
import battle.controllers.diego.strategy.*;
import battle.controllers.olmcts.*;
import battle.controllers.onesteplookahead.*;
import battle.controllers.nullController.NullController;
import battle.controllers.onesteplookahead.OneStepLookAhead;
import battle.controllers.random.RandomController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * Created by simon lucas on 10/06/15.
 */
public class BattleTest {
    BattleView view;

    static final int COEV = 0;
    static final int GA = 1;
    static final int RND = 2;
    static final int NULL = 3;
    static final int WASD = 4;
    static final int ARROWS = 5;
    static final int ONESTEP = 6;
    static final int OLMCTS = 7;
    static final int SSTEP = 8;

    public static int MAX_TICKS_GAME = 2000;
    public static int NUM_GAMES_TO_PLAY = 1;
    public static int SCORE_FUNC = 1;

    public static final boolean SHOW_ROLLOUTS = true;

    public static void main(String[] args) {
        //playOne(BattleTest.WASD, BattleTest.ARROWS);

        playOne(BattleTest.GA, BattleTest.COEV);
        //playOne(BattleTest.GA, BattleTest.COEV);
        //
        //playOne(BattleTest.ONESTEP, BattleTest.COEV);
        //playOne(BattleTest.GA, BattleTest.OLMCTS);
        Search.NUM_ACTIONS_INDIVIDUAL = 10;
        Search.MACRO_ACTION_LENGTH = 1;
        //playN(BattleTest.GA, BattleTest.COEV, "plots/data/GA-RND_"+Search.NUM_ACTIONS_INDIVIDUAL+"x"+Search.MACRO_ACTION_LENGTH+"_vs_COEV_"+NUM_GAMES_TO_PLAY+"x"+MAX_TICKS_GAME+".txt");
    }
    

    public static void playOne(int ply1, int ply2)
    {
        boolean visuals = true;
        SimpleBattle battle = new SimpleBattle(visuals, MAX_TICKS_GAME, SCORE_FUNC);
        BattleController p1 = createPlayer(ply1);
        BattleController p2 = createPlayer(ply2);

        double []res = battle.playGame(p1, p2);
    }

    public static void playN(int ply1, int ply2, String filename)
    {
        boolean visuals = false;
        double[][] results = new double[NUM_GAMES_TO_PLAY][MAX_TICKS_GAME*3];

        for(int i = 0; i < NUM_GAMES_TO_PLAY; ++i) {

            SimpleBattle battle = new SimpleBattle(visuals, MAX_TICKS_GAME, SCORE_FUNC);
            BattleController p1 = createPlayer(ply1);
            BattleController p2 = createPlayer(ply2);

            double []res = battle.playGame(p1, p2);
            System.arraycopy(res, 0, results[i], 0, MAX_TICKS_GAME*3);
        }

        System.out.println("Done.");
        dump(results, filename);
    }

    private static void dump(double[][] results, String filename)
    {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));

            for (int i = 0; i < results.length; ++i) {
                for (int j = 0; j < results[i].length; ++j) {
                    writer.write(results[i][j] + ",");
                }
                writer.write("\n");
            }

            writer.close();

        }catch(Exception e)
        {
            System.out.println("MEH: " + e.toString());
            e.printStackTrace();
        }
    }


    public static BattleController createPlayer(int ply)
    {
        Random rnd1 = new Random();

        switch (ply)
        {
            case BattleTest.COEV:
                return new BattleEvoController(new CoevSearch(
                        new UniformCrossover(rnd1),
                        new PMutation(rnd1, 0.1),
                        new TournamentSelection(rnd1, 3),
                        new RandomPairing(rnd1, 3),
                        rnd1));

            case BattleTest.GA:
                return new BattleEvoController(new GASearch(
                        new UniformCrossover(rnd1),
                        new PMutation(rnd1, 0.1),
                        new TournamentSelection(rnd1, 3),
                        new NullOpponentGenerator(Search.NUM_ACTIONS_INDIVIDUAL),
                        //new RndOpponentGenerator(rnd1),
                        rnd1));
            case BattleTest.ONESTEP:
                return new OneStepLookAhead();
            case BattleTest.OLMCTS:
                return new SingleMCTSPlayer(rnd1);
            case BattleTest.NULL:
                return new NullController();
            case BattleTest.RND:
                return new RandomController(rnd1);
            case BattleTest.WASD:
                return new WASDController();
            case BattleTest.ARROWS:
                return new ArrowsController();
            case BattleTest.SSTEP:
                return new SeveralStepsLookAhead(rnd1);
        }

        return new ArrowsController();

    }


}
