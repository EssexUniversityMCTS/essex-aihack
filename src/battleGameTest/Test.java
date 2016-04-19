package battleGameTest;

import battle.BattleTest;
import battle.SimpleBattle;
import battle.controllers.diego.search.Search;

/**
 * Created by jliu on 19/04/16.
 */
public class Test {
    public static int player1 = 1;
    public static int player2 = 0;
    public static String[] playerNames = {"COEV", "GA", "RND", "NULL", "WASD", "ARROWS", "ONESTEP", "OLMCTS", "SSTEP"};
    public static boolean screen = true;
    static BattleTest mytest;

    public static void main(String[] args) {
        BattleTest.MAX_TICKS_GAME = 2000;
        BattleTest.NUM_GAMES_TO_PLAY = 1;

        SimpleBattle.DURATION_PER_TICK = 10;
        SimpleBattle.missilesBudget = 2500;
        SimpleBattle.missileSpeed = 4;
        SimpleBattle.cooldown = 4;
        SimpleBattle.life = 3;
        SimpleBattle.minShootRange = 30;
        SimpleBattle.missileCostPenality = 1;
        SimpleBattle.missileBudgetPenality = 1;


        // prepare output
        String player1name = playerNames[player1];
        if(player1==1 || player1==0)
            player1name = player1name + "_" + Search.NUM_ACTIONS_INDIVIDUAL+"x"+Search.MACRO_ACTION_LENGTH;
        String player2name = playerNames[player2];
        if(player2==1 || player2==0)
            player2name = player2name + "_" + Search.NUM_ACTIONS_INDIVIDUAL+"x"+Search.MACRO_ACTION_LENGTH;

        // run the game
        if(screen) {
            mytest.playOne(player1, player2);
        } else {
            mytest.playN(player1, player2, "data/" + player1name + "_vs_" + player2name + "_" + BattleTest.MAX_TICKS_GAME + "x" + BattleTest.NUM_GAMES_TO_PLAY + ".txt");
        }
    }
}
