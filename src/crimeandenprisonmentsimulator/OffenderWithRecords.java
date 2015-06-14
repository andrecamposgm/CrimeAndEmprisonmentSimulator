package crimeandenprisonmentsimulator;


/**
 * This class represents an {@link Offender} that has committed a crime. 
 * This means this agent was already an {@link Encarcerated} agent. 
 * @author Andre Campos andreloc@gmail.com
 */
public class OffenderWithRecords extends Offender {

	private static Integer averageRateOfReoffending;
	
	public OffenderWithRecords(Encarcerated encarcerated) {
		super(encarcerated);
	}

	public static void setAverageRateOfReoffending(Integer integer) {
		OffenderWithRecords.averageRateOfReoffending = integer;
	}

	@Override
	protected int getRateOfOffending() {
		return averageRateOfReoffending;
	}
}
