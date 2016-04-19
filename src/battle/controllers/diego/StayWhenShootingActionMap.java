package battle.controllers.diego;

import asteroids.Action;

import java.util.Random;

/**
 * Created by jliu on 19/04/16.
 */
public class StayWhenShootingActionMap {
// It's "move when shooting"

    public static Action[] ActionMap = new Action[]{
            new Action(0.0,0.0,false),  //0
            new Action(0.0,-1.0,false), //1
            new Action(0.0,1.0,false),  //2
            new Action(1.0,0.0,false),  //3
            new Action(1.0,-1.0,false), //4
            new Action(1.0,1.0,false),  //5
            new Action(0.0,0.0,true),   //6
            new Action(0.0,-1.0,true),  //7
            new Action(0.0,1.0,true),   //8
            new Action(1.0,0.0,true),   //9
            new Action(1.0,-1.0,true),  //10
            new Action(1.0,1.0,true)    //11
    };

    /**
     * Mutate thrust 0->1 or 1->0
     * @param action
     * @return action index after mutation
     */
    public static int mutateThrust(int action)
    {
        if(action%6 < 3)
            return (action+3);
        else
            return (action-3);
    }

    /**
     * Mutate the steer
     * @param action
     * @param clockwise
     * @return action index after mutation
     */
    public static int mutateSteer(int action, boolean clockwise)
    {
        // From a side to center.
        if(action%3!=0) {
            return (action - (action % 3));
        } else { // From center to one side
            if (clockwise)
                return (action + 2);
            else
                return (action + 1);
        }
    }

    /**
     * Shooting
     * @param action
     * @return action index after mutation
     */
    public static int mutateShooting(int action)
    {
        if(action < 6)
            return (action + 6);
        else
            return (action - 6);
    }
}
