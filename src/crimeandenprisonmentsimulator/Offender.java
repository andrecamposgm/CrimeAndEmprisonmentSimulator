/**
 * 
 */
package crimeandenprisonmentsimulator;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.util.ContextUtils;

/**
 * An offender is an human bean that may commit crimes on the society. 
 * @author Andre Campos andreloc@gmail.com 
 */
public abstract class Offender extends Human {

	private static Double imprisonmentProbability;
	
	private static final Random imprisonmentRandomizer = new Random();
	private static final Random crimeRandomizer = new Random();
	private boolean commitedCrimeNow = false; 
	
	public Offender() { 
	}
	
	public Offender(Human human) { 
		super(human);
	}
	
	protected boolean willBeInprisonedNow() {
		if(commitedCrimes == 0) { 
			return false;
		} else { 
			return (imprisonmentRandomizer.nextDouble() * 100 <= imprisonmentProbability);
		}
	}

	public static void setImprisonmentProbability(Double integer) {
		Offender.imprisonmentProbability = integer;
	}
	
	
	@Override
	protected void anniversary() {
		if(willCommitACrimeNow()) { 
			commitedCrimes++;
		}
		
		if(willBeInprisonedNow()) { 
			encarcerate();
		}
	}
	
	private boolean willCommitACrimeNow() {
		 commitedCrimeNow = (crimeRandomizer.nextDouble() * 100 <= getRateOfOffending());
		 return commitedCrimeNow;
	}
	
	protected abstract int getRateOfOffending();

	@SuppressWarnings("unchecked")
	private void encarcerate() {
		Context<Object> context = ContextUtils.getContext(this);
		context.add(new Encarcerated(this));
		context.remove(this);		
	}
	
	public int getCommitedCrimes(){
		return commitedCrimes;
	}
	
	public int commitedCrimeNow(){
		return commitedCrimeNow ? 1:0;
	}
	
}
