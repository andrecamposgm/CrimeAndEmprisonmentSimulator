package crimeandenprisonmentsimulator;

/**
 * This class represents an {@link Offender} that has no criminal records. 
 * This means this agent was never an {@link Encarcerated} agent. 
 * @author Andre Campos andreloc@gmail.com
 */
public class OffenderWithoutRecords extends Offender {

	private static Integer averageRateOfOffending;

	public static void setAverageRateOfOffending(Integer integer) {
		OffenderWithoutRecords.averageRateOfOffending = integer;
	}

	@Override
	protected int getRateOfOffending() {
		return averageRateOfOffending;
	}
}
