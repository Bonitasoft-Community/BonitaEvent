# BonitaEvent
Java Library to manage errors and informations. Don't throws an exception, create an event where you can give an ErrorCode, a Title, a Cause, a WhatToDo, a Consequence to the final user
 
 Son in your software, create some events like :
    private final BEvent inputDivisionEvent = new BEvent(BEventUsage.class.getName(), 1, Level.INFO, "Calculate Division", "Run a division");
    private final BEvent resultDivisionEvent = new BEvent(BEventUsage.class.getName(), 2, Level.SUCCESS, "Division Done", "Result of the division");
     private final BEvent errorDivisionEvent = new BEvent(BEventUsage.class.getName(), 3, Level.ERROR, "Division Error", "An error arrive in the division", "The result is not available", "Check the number you give at input : divide by 0 is not possible for example");
     
    On a INFO or SUCCESS event, the title and the cause are enougth.
    On a error (APPLICATIONERROR, ERROR), you must give the cause, but the consequence (here, the consequence is that the result is not available) and the action to perform to solve the issue. 
    Who are the best to give theses information ? The developper of course.
     
     When you want to use the event, you can give parameters (interresting to give in the event the divider isn't ?). So this event is created in reference of the ErrorDivision, with the exeption in parameters 
     
       BEvent bevent = new BEvent(errorDivisionEvent, e, "Error with operand " + a + " / " + b);
       
What's more ? 
  - Don't stop at one error (is a compiler stop at the first error ? Of course not), so register the event in some list. Then, using the BEventFactory, you can manipulate the list (is there are an error in the list ?)
  - normalize the log : it's not your job to log the event. Just call bevent.log() and that's it ! The log is created, normalized.
  - result to the user ? In the BEventFactory, you can transform the event (or the list of event) in a nice HTML view. Ready to send to the browser ! Look the custom page Ping to have an example.
         
        
 1.2 add the BEventFactory.getSyntheticHtml() method
