package battleGameTest;

import battle.BattleTest;
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
import math.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import math.MutableDouble;

/**
 * Created by Jialin Liu on 12/04/16.
 */
public class TestWithMissile {

    public static int player1 = 0;
    public static int player2 = 0;
    public static String[] playerNames = {"COEV", "GA", "RND", "NULL", "WASD", "ARROWS", "ONESTEP", "OLMCTS", "SSTEP"};
    public static boolean screen = false; 
    static BattleTest mytest;

    public static void main(String[] args) {
        MutableDouble opt_value = new MutableDouble(0.0);
        if(args.length>1) {
            if(Util.findArgValue(args, "1", opt_value)) {
                player1 = opt_value.intValue();
                //System.out.println("player 1 " + player1);
            }
            if(Util.findArgValue(args, "2", opt_value)) {
                player2 = opt_value.intValue();
                //System.out.println("player 2 " + player2);
            }
            if(Util.findArgValue(args, "T", opt_value)) {
                BattleTest.MAX_TICKS_GAME = opt_value.intValue();
                //System.out.println("Time ticks " + BattleTest.MAX_TICKS_GAME);
            }
            if(Util.findArgValue(args, "N", opt_value)) {
                BattleTest.NUM_GAMES_TO_PLAY = opt_value.intValue();
                //System.out.println("Games " + BattleTest.NUM_GAMES_TO_PLAY);
            }
            if(Util.findArgValue(args, "num_actions", opt_value)) {
                Search.NUM_ACTIONS_INDIVIDUAL = opt_value.intValue();
                //System.out.println("NUM_ACTIONS_INDIVIDUAL " + Search.NUM_ACTIONS_INDIVIDUAL);
            }
            if(Util.findArgValue(args, "macro_actions", opt_value)) {
                Search.MACRO_ACTION_LENGTH = opt_value.intValue();
                //System.out.println("MACRO_ACTION_LENGTH " + Search.MACRO_ACTION_LENGTH);
            }
            if(Util.findArgValue(args, "mcost", opt_value)) {
                BattleTest.SCORE_FUNC = opt_value.intValue();
                //System.out.println("missile cost = -1 ");
            }
            if(Util.findArgValue(args, "screen", opt_value)) {
                screen = true;
            }
        }
        
        String player1name = playerNames[player1];
        if(player1==1 || player1==0)
            player1name = player1name + "_" + Search.NUM_ACTIONS_INDIVIDUAL+"x"+Search.MACRO_ACTION_LENGTH;
        String player2name = playerNames[player2];
        if(player2==1 || player2==0)
            player2name = player2name + "_" + Search.NUM_ACTIONS_INDIVIDUAL+"x"+Search.MACRO_ACTION_LENGTH;
        
        if(screen) {
            mytest.playOne(player1, player2);
        } else {
            mytest.playN(player1, player2, "data_TestWithMissile/" + player1name + "_vs_" + player2name + "_" + BattleTest.MAX_TICKS_GAME + "x" + BattleTest.NUM_GAMES_TO_PLAY + ".txt");
        }
    }
}
