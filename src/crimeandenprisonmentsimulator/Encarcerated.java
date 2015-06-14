package crimeandenprisonmentsimulator;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.util.ContextUtils;

/**
 * This class represents agents {@link Offender} that committed a crime and were punished.  
 * @author Andre Campos andreloc@gmail.com
 */
public class Encarcerated extends Human {

	/**
	 * Random variable used to generate the normal distribution responsible to calculate the 
	 * time in prison
	 */
	private static final Random timeImPrisonRandomizer = new Random();
	private static double averageTimeInPrison; 					// years
	private static double standardTimeDeviationTimeInPrison;
	private int expectedTimeInPrison;
	
	public Encarcerated(Offender fromOffender) { 
		super(fromOffender);
		expectedTimeInPrison = expectedTimeInPrison();
	}
	
	@Override
	protected void anniversary() {
		expectedTimeInPrison--;
		if(expectedTimeInPrison < 0) { 
			releaseFromPrison();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void releaseFromPrison() {
		Context<Object> context = ContextUtils.getContext(this);
		context.add(new OffenderWithRecords(this));
		context.remove(this);
	}

	private int expectedTimeInPrison() {
		return (int) (timeImPrisonRandomizer.nextGaussian() * 
				standardTimeDeviationTimeInPrison + 
				averageTimeInPrison);
	}
	
	public static void setAvgTimeInPrison(double value) {
		averageTimeInPrison = value;
	}

	public static void setStdDeviationForTimeInPrison(double value) {
		standardTimeDeviationTimeInPrison = value;
	}

}