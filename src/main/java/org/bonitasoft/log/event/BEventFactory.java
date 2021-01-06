package org.bonitasoft.log.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BEventFactory {

    /**
     * is this list contains one error ? If yes, then we return true
     */
    public static boolean isError(final List<BEvent> listEvents) {
        for (final BEvent event : listEvents) {
            if (event.isError()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param listEvents
     * @return
     */
    public static String getHtml(final List<BEvent> listEvents) {
        StringBuilder tableHtml = new StringBuilder();
        tableHtml.append("<table>");
        for (final BEvent event : listEvents) {
            tableHtml.append("<tr><td>" + event.getHtml() + "</td></tr>");
        }
        tableHtml.append("</table>");
        return tableHtml.toString();
    }

    /**
     * create the list in a synthetic way, as a list of events
     * 
     * @param listEvents
     * @return
     */
    public static String getSyntheticHtml(final List<BEvent> listEvents) {
        StringBuilder tableHtml = new StringBuilder();

        tableHtml.append("<table style=\"border:1px solid black;border-spacing: 10px 0px;border-collapse: separate;\">");
        tableHtml.append("<tr><td>Title</td><td>Level</td><td>Parameters</td></tr>");

        for (final BEvent event : listEvents) {
            tableHtml.append("<tr><td>" + event.getHtmlTitle() + "</td>"
                    + "<td>" + event.getLevel().toString() + "</td>"
                    + "<td>" + event.getParameters() + event.getExceptionDetails() + "</td>"
                    + "</tr>");
        }
        tableHtml.append("</table>");
        return tableHtml.toString();
    }

    /**
     * return a list of event for a log
     * 
     * @param listEvents
     * @return
     */
    public static String getSyntheticLog(final List<BEvent> listEvents) {
        StringBuilder tableLog = new StringBuilder();

        for (final BEvent event : listEvents) {
            tableLog.append(event.toString() + " <~> ");
        }

        return tableLog.toString();
    }

    /**
     * return a synthetic log only for errors
     * 
     * @param listEvents
     * @return
     */
    public static String getSyntheticErrorLog(final List<BEvent> listEvents) {
        StringBuilder tableLog = new StringBuilder();

        for (final BEvent event : listEvents) {
            if (event.isError())
                tableLog.append(event.toString() + " <~> ");
        }
        return tableLog.toString();
    }

    /**
     * Json to listEvent
     */
    public static List<BEvent> getListEventsFromJson(List<Map<String, Serializable>> listEventsJson) {
        List<BEvent> listEvents = new ArrayList<>();
        for (Map<String, Serializable> eventJson : listEventsJson)
            listEvents.add(BEvent.getInstanceFormJson(eventJson));
        return listEvents;
    }

    /**
     * Event to Json
     * 
     * @param listEvents
     * @return
     */
    public static List<Map<String, Serializable>> getJsonFromListEvents(List<BEvent> listEvents) {
        List<Map<String, Serializable>> listEventsJson = new ArrayList<>();
        for (BEvent event : listEvents)
            listEventsJson.add(event.getJson(false));
        return listEventsJson;
    }

    /**
     * add the event in the list only if this event is a new one, in order to remove the duplication.
     * An event already exist if this is the same package/number/parameters (see BEvent.same() ).
     *
     * @param listEvents the list modified if needed
     * @param event the new event to add
     */
    public static void addEventUniqueInList(final List<BEvent> listEvents, final BEvent eventToAdd) {
        if (listEvents == null) {
            return;
        }

        for (int i = 0; i < listEvents.size(); i++) {
            if (listEvents.get(i).isIdentical(eventToAdd)) {
                return;
            }
        }
        listEvents.add(eventToAdd);
    }

    /**
     * add a list of events in the list only if this event is a new one, in order to remove the duplication.
     * An event already exist if this is the same package/number/parameters (see BEvent.same() ).
     *
     * @param listEvents
     * @param eventsToAdd
     */
    public static void addListEventsUniqueInList(final List<BEvent> listEvents, final List<BEvent> eventsToAdd) {
        for (final BEvent event : eventsToAdd) {
            addEventUniqueInList(listEvents, event);
        }

    }

    /**
     * calculate from a list of event a UNIQUE list of event, keeping the same order.
     * An event already exist if this is the same package/number/parameters (see BEvent.same() ).
     *
     * @param listEvents
     * @return
     */
    public static List<BEvent> filterUnique(final List<BEvent> listEvents) {
        final List<BEvent> listUnique = new ArrayList<BEvent>();

        for (final BEvent event : listEvents) {
            boolean alreadyExist = false;
            for (final BEvent existingEvent : listUnique) {
                if (event.isIdentical(existingEvent)) {
                    alreadyExist = true;
                    break;
                }
            }
            if (!alreadyExist) {
                listUnique.add(event);
            }
        }
        return listUnique;
    }

}
