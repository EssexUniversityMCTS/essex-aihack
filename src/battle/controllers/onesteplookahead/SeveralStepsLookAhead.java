
package battle.controllers.onesteplookahead;

import battle.SimpleBattle;
import battle.controllers.diego.ActionMap;
import math.Pair;
import battle.BattleController;
import asteroids.Action;
import utilities.ElapsedCpuTimer;
import java.util.*;
import math.Util;

import java.awt.*;
/**
 * Author: Jialin Liu, University of Essex                                    
 * Date: 01/04/2016 
 */
public class SeveralStepsLookAhead implements BattleController {
    
    private int NUM_ACTIONS = 1;

    private int num_paths;
    
    private double[] weights;

    private double[] proba;

    private final double gamma = 0.15;
    private final double eta = 0.3;
    
    public static Random m_rnd;
    
    public int playerID;

    /**
     * Constructor of the engine.
     */
    public SeveralStepsLookAhead(Random rnd, int _num_actions)
    {
        this(rnd);
        this.NUM_ACTIONS = _num_actions;
    }

    public SeveralStepsLookAhead(Random rnd)
    {
        this.m_rnd = rnd;
    }

    /**
     * Initializes the engine.
     * This function is also called to reset it.
     */
    public void init(SimpleBattle gameState, int playerId)
    {
        this.playerID = playerId;
        this.num_paths = (int) Math.pow(ActionMap.ActionMap.length, this.NUM_ACTIONS);
        this.weights = new double[this.num_paths];
        for(int i=0; i<this.num_paths; i++)
            this.weights[i] = 1;
    }
    
    @Override
    public Action getAction(SimpleBattle gameState, int playerId, ElapsedCpuTimer elapsedTimer)
    {
        long avgTimeTaken = 0;
        long acumTimeTaken = 0;
        int numIters = 0;
        long remainingLimit = 0;
        //long remaining = (long) (elapsedTimer.getMaxTime()/1000000.0);
        ElapsedCpuTimer testTimer = new ElapsedCpuTimer();
        testTimer.setMaxTime(elapsedTimer.getMaxTime());
        long remaining = testTimer.remainingTimeMillis();
        while(remaining > 2*avgTimeTaken && remaining > remainingLimit){
            SimpleBattle thisGameCopy = gameState.clone();
            ElapsedCpuTimer elapsedTimerIteration = new ElapsedCpuTimer();
            // update the weights
            double sumWeights = 0;
            for(int i=0; i<this.num_paths; i++)
                sumWeights += this.weights[i];
            for(int i=0; i<this.num_paths; i++)
                this.proba[i] = (1-this.gamma)*this.weights[i]/sumWeights + this.gamma/this.num_paths;
            // choose actions
            int chosenActionsIdx = Util.randomIntWithProb(this.proba);
            int[] chosenActions = Util.intSequence(ActionMap.ActionMap.length, this.NUM_ACTIONS, chosenActionsIdx);
            int opponentActionsIdx = 0;
            // make the actions
            for(int i=0; i<this.NUM_ACTIONS; i++) {
                int opponentAction = this.m_rnd.nextInt(NUM_ACTIONS);
                thisGameCopy.update(ActionMap.ActionMap[chosenActions[i]], ActionMap.ActionMap[opponentAction]);
                opponentActionsIdx += opponentAction*Math.pow(ActionMap.ActionMap.length, this.NUM_ACTIONS-i+1);
            }
            // get the score
            double score = thisGameCopy.getScore(playerId);
            // update the weights
            for(int i=0; i<this.num_paths; i++)
                this.weights[i] *= Math.exp(this.gamma*score/this.proba[i]/this.num_paths);
            
            numIters++;
            acumTimeTaken += (elapsedTimerIteration.elapsedMillis()) ;
            avgTimeTaken  = acumTimeTaken/numIters;
            remaining = testTimer.remainingTimeMillis();
        }
        
        // recommend
        int bestAction = Util.randomIntWithProb(this.proba);
        //System.out.println("action : " + best_action);
        return ActionMap.ActionMap[bestAction];
   }

    public void draw(Graphics2D g) {}
}
