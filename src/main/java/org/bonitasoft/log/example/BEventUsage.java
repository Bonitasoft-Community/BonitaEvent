package org.bonitasoft.log.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bonitasoft.log.event.BEvent;
import org.bonitasoft.log.event.BEvent.Level;
import org.bonitasoft.log.event.BEventFactory;

public class BEventUsage {

    // declare events
    private final BEvent inputDivisionEvent = new BEvent(BEventUsage.class.getName(), 1, Level.INFO,
            "Calculate Division", "Run a division");
    private final BEvent resultDivisionEvent = new BEvent(BEventUsage.class.getName(), 2, Level.SUCCESS,
            "Division Done", "Result of the division");
    private final BEvent errorDivisionEvent = new BEvent(BEventUsage.class.getName(), 3, Level.ERROR, "Division Error",
            "An error arrive in the division",
            "The result is not available",
            "Check the number you give at input : divide by 0 is not possible for example");

    public class DivisionResult {

        List<BEvent> listEvents = new ArrayList<BEvent>();
        Double resultOfDivision;
    }

    /**
     * write a method using event
     *
     * @param a
     * @param b
     * @return
     */
    public DivisionResult calculDivision(final double a, final double b) {
        final DivisionResult divisionResult = new DivisionResult();
        divisionResult.listEvents.add(new BEvent(inputDivisionEvent, "Divide " + a + " by " + b));
        try {
            divisionResult.resultOfDivision = a / b;
            divisionResult.listEvents
                    .add(new BEvent(resultDivisionEvent, " Result is " + divisionResult.resultOfDivision));
        } catch (final Exception e) {
            final BEvent bevent = new BEvent(errorDivisionEvent, e, "Error with operand " + a + " / " + b);
            bevent.log();

            divisionResult.listEvents.add(bevent);
        }
        return divisionResult;
    }

    /**
     * use the method
     */
    public void letsDivide() {
        final Logger logger = Logger.getLogger(BEventUsage.class.getName());
        DivisionResult divisionResult = calculDivision(23.4, 551);
        if (BEventFactory.isError(divisionResult.listEvents)) {
            logger.severe("Error ! ");
        }
        divisionResult = calculDivision(23.4, 0);
        if (BEventFactory.isError(divisionResult.listEvents)) {
            logger.severe("Error ! ");
        }
    }

}
