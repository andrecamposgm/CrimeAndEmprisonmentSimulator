/**
 * 
 */
package crimeandenprisonmentsimulator;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.util.ContextUtils;

/**
 * Basic representation of an human bean. It will have anniversary 
 * @author Andre Campos andreloc@gmail.com 
 */
public abstract class Human {

	private static final Random ageRandomizer = new Random();
	private static int averagePopulationAge;
	private static int standardDeviationOfPopulationAge;
	private static int initialCriminalAge = 18;

	private int age;
	private int death;
	protected int commitedCrimes = 0;
	
	public Human() { 
		age 	= initialCriminalAge;
		death 	= calculateDeath();
	}
	
	public Human(Human fromOtherHuman) { 
		age 			= fromOtherHuman.age;
		death 			= fromOtherHuman.death;
		commitedCrimes 	= fromOtherHuman.commitedCrimes;
	}

	@ScheduledMethod(start = 1, interval = 0.1)
	public final void anniversaryInternal() { 
		age++;
		if(age > death) {
			die();
		} else { 
			anniversary();
		}
	}

	/**
	 * This method will be called every year
	 */
	protected abstract void anniversary();
	
	public final int getAge() { 
		return age;
	}
	
	public final int getDeathAge() { 
		return death;
	}
	
	public static void setAvgAge(int avgAge) { 
		averagePopulationAge = avgAge;
	}
	
	public static void setInitialCriminalAge(int initialAge) { 
		initialCriminalAge = initialAge;
	}
	
	public static void setStandardDeviationOfPopulationAge(int stdDeviationOfPopulationAge) { 
		standardDeviationOfPopulationAge = stdDeviationOfPopulationAge;
	}

	@SuppressWarnings("unchecked")
	private void die() {
		Context<Object> context = ContextUtils.getContext(this);
		context.remove(this);
		context.add(new OffenderWithoutRecords());
	}

	/**
	 * Basically generates a death time for the agent based on its 
	 * @return
	 */
	private int calculateDeath() {
		return (int) (ageRandomizer.nextGaussian() * // uniform distribution 
				standardDeviationOfPopulationAge +   // standard deviation value in ages 
				averagePopulationAge);               // average population age
	}

}
