package battle.controllers.diego.strategy;

import battle.controllers.diego.ActionMap;
import battle.controllers.diego.search.GAIndividual;

import java.util.Random;

/**
 * Created by dperez on 08/07/15.
 */
public class PMutation implements IMutation
{
    double mutProb;
    Random m_rnd;

    public PMutation(Random rnd, double mutp)
    {
        mutProb = mutp;
        m_rnd = rnd;
    }

    @Override
    public void mutate(GAIndividual individual) {

        for (int i = 0; i < individual.m_genome.length; i++) {
            if(m_rnd.nextDouble() < mutProb)
            {
                double mutProba = m_rnd.nextDouble();
                if(mutProba < 3/7)  //mutate thrust
                    individual.m_genome[i] = ActionMap.mutateThrust(individual.m_genome[i]);
                else if (mutProba < 6/7)  //mutate steering
                    individual.m_genome[i] = ActionMap.mutateSteer(individual.m_genome[i], m_rnd.nextDouble()>0.5);
                else //mutate shooting
                    individual.m_genome[i] = ActionMap.mutateShooting(individual.m_genome[i]);
            }

        }
    }

}
