/**
 * 
 */
package crimeandenprisonmentsimulator;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

/**
 * I = (P(I)*S)*(N*λ) - Size of incarcerated population
 * I - Incarcerated population
 * 
 * S - Average time served in prison
 * P(I) - probability of imprisonment for committing a crime 
 * 
 * N - Number of offenders 
 * λ - average rate of offending. 
 * 
 * N2 - Number of offenders by the second time
 * λ2 - Average rate of offending by the second time.
 * 
 * (P(I)*S) - Expected prison price per crime committed
 * (N*λ)    - Total number of crimes committed
 * 
 * @author andreloc@gmail.com
 */
public class SimulationBuilder implements ContextBuilder<Object>{

	private static final String AVG_AGE = "average_age";
	private static final String STD_DEVIATION_AGE = "age_standard_deviation";
	
	private static final String AVG_TIME_IN_PRISON = "average_time_in_prison";
	private static final String STD_DEVIATION_FOR_TIME_IN_PRISON = "standard_deviation_for_time_in_prison";
	
	private static final String IMPRISONMENT_PROBABILITY = "imprisonment_probability";
	
	private static final String NUMBER_OF_OFFEENDERS = "number_of_offenders";
	private static final String AVERAGE_RATE_OF_OFFENDING = "average_rate_of_offending";
	
	private static final String AVERAGE_RATE_OF_REOFFENDING = "average_rate_of_reoffending";
	
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("CrimeAndEmprisonmentSimulator");
		Parameters parameters = RunEnvironment.getInstance().getParameters();

		// Population age 
		Human.setAvgAge(parameters.getInteger(AVG_AGE));
		Human.setStandardDeviationOfPopulationAge(parameters.getInteger(STD_DEVIATION_AGE));
		
		// Imprisonment probability 
		Offender.setImprisonmentProbability(parameters.getInteger(IMPRISONMENT_PROBABILITY));

		// Offenders without crimes 
		OffenderWithoutRecords.setAverageRateOfOffending(parameters.getInteger(AVERAGE_RATE_OF_OFFENDING));
		// Offender with crime records 
		OffenderWithRecords.setAverageRateOfReoffending(parameters.getInteger(AVERAGE_RATE_OF_REOFFENDING));

		// Encarcerated probabilities
		Encarcerated.setAvgTimeInPrison(parameters.getInteger(AVG_TIME_IN_PRISON));
		Encarcerated.setStdDeviationForTimeInPrison(parameters.getInteger(STD_DEVIATION_FOR_TIME_IN_PRISON));
		
		Grid<Object> grid 				= createGrid(context);
		ContinuousSpace<Object> space 	= createSpace(context);
		
		createInitialOffenders(context, parameters);
		randomizeOffenderPosition(context, grid, space);
		
		return context;
	}

	private void randomizeOffenderPosition(Context<Object> context, Grid<Object> grid,
			ContinuousSpace<Object> space) {
		for (Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}
	}

	private void createInitialOffenders(Context<Object> context,
			Parameters parameters) {
		int numberOfOffenders = parameters.getInteger(NUMBER_OF_OFFEENDERS);
		for(int i = 0; i<numberOfOffenders; i++){
			context.add(new OffenderWithoutRecords());
		}
	}

	private ContinuousSpace<Object> createSpace(Context<Object> context) {
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		return spaceFactory.createContinuousSpace(
				"space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50, 50);
	}

	private Grid<Object> createGrid(Context<Object> context) {
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		return gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new WrapAroundBorders(),
						new SimpleGridAdder<Object>(), true, 50, 50));
	}
}
