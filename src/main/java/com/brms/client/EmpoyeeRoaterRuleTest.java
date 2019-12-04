package com.brms.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import org.dmg.pmml.pmml_4_2.descr.Scorecard;
import org.drools.core.ClassObjectFilter;
import org.drools.core.command.runtime.rule.QueryCommand;
import org.drools.core.marshalling.impl.ProtobufMessages.FactHandle;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

import com.idexx.idexxdemo.ValidationDomain;

import employeerostering.employeerostering.DayOffRequest;
import employeerostering.employeerostering.Employee;
import employeerostering.employeerostering.Shift;
import employeerostering.employeerostering.ShiftAssignment;
import employeerostering.employeerostering.Skill;
import employeerostering.employeerostering.Timeslot;



public class EmpoyeeRoaterRuleTest {
    
    public KieContainer createContainer() {
    	System.out.println("kie container creation...");
    	KieServices ks = KieServices.Factory.get();
    	//ReleaseId rId = ks.newReleaseId("citi", "cbol", "1.1");
    	//ReleaseId rId = ks.newReleaseId("redhatcentral", "demo1", "1.3");
    	ReleaseId rId = ks.newReleaseId("redhat", "appointment", "1.0");
    	//ReleaseId rId = ks.newReleaseId("test", "Test", "1.4") ;
    	KieContainer kContainer = ks.newKieContainer(rId);
    	KieScanner scanner =  ks.newKieScanner(kContainer);
    	scanner.start(60000);
    	return kContainer;
    }
	public static void main(String[] args){
		
		EmpoyeeRoaterRuleTest obj  = new EmpoyeeRoaterRuleTest();
		
		//List<HashMap<String, String>> result = obj.balancesDisplayedForProductMap("HKGCB", "742", "1", "0");
		//obj.eventTest1();
		obj.eventTest3();
		//System.out.println(result);
	}
public void eventTest3(){
	KieServices ks = KieServices.Factory.get();
	//ReleaseId rId = ks.newReleaseId("citi", "cbol", "1.1");
	//ReleaseId rId = ks.newReleaseId("redhatcentral", "demo1", "1.3");
	ReleaseId rId = ks.newReleaseId("employeerostering", "employeerostering", "1.0.8");
	//ReleaseId rId = ks.newReleaseId("test", "Test", "1.4") ;
	KieContainer kContainer = ks.newKieContainer(rId);
		//StatelessKieSession kSession = kContainer.newStatelessKieSession("rulesession");
		KieSession kSession = kContainer.newKieSession("rulesession");
		
		KieRuntimeLogger logger;
    	logger = ks.getLoggers().newFileLogger(kSession, "/Users/jpaulraj/ruleaudit/employeeRoster");
		
		  KieCommands commandsFactory =
	         		KieServices.Factory.get().getCommands();
		
		Skill skill = new Skill("chess","1",1);
        Skill skill2 = new Skill("chess1","1",1);
       
        //DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "H",LocalTime.of(16,0,0),LocalTime.of(19,0,0) ,2);
        //DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "H",LocalTime.of(16,0,0),LocalTime.of(17,0,0),2 );
        DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "F",2);
        //dayOffRequest.setTimeOffHrs(0);
        Employee employee = new Employee("Jey1",
                Arrays.asList(skill),1);
        Employee employee2 = new Employee("Jey2",
        			Arrays.asList(skill2), 2);
        Employee employee3 = new Employee("Jey3",
        			Arrays.asList(skill2),3);
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2017,
                                                          1,
                                                          1,
                                                          7,
                                                          0),
                                         LocalDateTime.of(2017,
                                                          1,
                                                          1,
                                                          15,
                                                          0));
        Timeslot timeslot2 = new Timeslot(LocalDateTime.of(2017,
                1,
                1,
                16,
                0),
LocalDateTime.of(2017,
                1,
                1,
                23,
                0));
        
        Timeslot timeslot3 = new Timeslot(LocalDateTime.of(2017,
                1,
                2,
                7,
                0),
LocalDateTime.of(2017,
                1,
                2,
                15,
                0));
Timeslot timeslot4 = new Timeslot(LocalDateTime.of(2017,
1,
2,
16,
0),
LocalDateTime.of(2017,
1,
2,
23,
0));
        
        Shift shift = new Shift(timeslot,skill);
        Shift shift2 = new Shift(timeslot2,skill2);
        Shift shift3 = new Shift(timeslot3,skill);
        Shift shift4 = new Shift(timeslot4,skill2);
        

        //DayOffRequest dayOffRequest = new DayOffRequest(employee2, LocalDate.of(2017,1,1), "H",LocalTime.of(15,0,0),LocalTime.of(19,0,0) );

        
        ShiftAssignment shiftAssignment = new ShiftAssignment();
        shiftAssignment.setShift(shift);
        ShiftAssignment shiftAssignment2 = new ShiftAssignment();
        shiftAssignment2.setShift(shift2);
        
        ShiftAssignment shiftAssignment3 = new ShiftAssignment();
        shiftAssignment3.setShift(shift3);
        ShiftAssignment shiftAssignment4 = new ShiftAssignment();
        shiftAssignment4.setShift(shift4);
        
        List<Object> employees = new ArrayList<Object>();
        List<Object> facts = new ArrayList<Object>();
        employees.add(employee3);
        employees.add(employee2);
        employees.add(employee);
        
        facts.add(shiftAssignment);
        facts.add(shiftAssignment2);
        facts.add(shiftAssignment3);
        facts.add(shiftAssignment4);
      /* facts.add(employee);
        facts.add(employee2);
        facts.add(employee3);*/

        List<Object> timeoOffFacts = new ArrayList<Object>();
        timeoOffFacts.add(dayOffRequest);
        //Command<?> startEmp =  commandsFactory.newStartProcess("belldemo.techservice", param);	
        Command<?> assigmmentFacts = commandsFactory.newInsertElements(facts, "assignmnets");
        Command<?> employeeFacts = commandsFactory.newInsertElements(employees,"employee");
        Command<?> timeoff = commandsFactory.newInsertElements(timeoOffFacts,"timeOff");
        //Command<?> assignmentQuery =  commandsFactory.newQuery("AssignedShifts", "AssignedShifts") ;
        //Command<?> unAssignmentQuery =  commandsFactory.newQuery("unAssignedShifts","unAssignedShifts") ;
        QueryCommand assignmentQuery = new QueryCommand();
        assignmentQuery.setName("AssignedShifts");
        assignmentQuery.setOutIdentifier("AssignedShifts");
    	 QueryCommand unAssignmentQuery = new QueryCommand();
    	 unAssignmentQuery.setName("unAssignedShifts");
    	 unAssignmentQuery.setOutIdentifier("unAssignedShifts");
        Command<?> dispose  = commandsFactory.newDispose();
        Command<?> fireAllRules = commandsFactory.newFireAllRules();
       // Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(assigmmentFacts,fireAllRules,assignmentQuery,unAssignmentQuery),"rulesession");

        Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(assigmmentFacts,employeeFacts,timeoff,fireAllRules,assignmentQuery,unAssignmentQuery,dispose),"rulesession");
        //Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(assigmmentFacts,employeeFacts,fireAllRules,assignmentQuery,unAssignmentQuery,dispose),"rulesession");
        
        Marshaller marshaller = MarshallerFactory.getMarshaller( MarshallingFormat.JSON, getClass().getClassLoader() );
        String xStreamXml = marshaller.marshall( batchCommand );
        System.out.println("\t######### Rules request"  + xStreamXml);

        kSession.execute(batchCommand);
		QueryResults results = kSession.getQueryResults("AssignedShifts");
		QueryResults unresults = kSession.getQueryResults("unAssignedShifts");
		
		QueryResultsRow resultsRow = results.iterator().next();
		List<ShiftAssignment> shiftAssignments = (List<ShiftAssignment>) resultsRow.get("assigned");
		
		QueryResultsRow unresultsRow = unresults.iterator().next();

		List<ShiftAssignment> unAssignedshift= (List<ShiftAssignment>) unresultsRow.get("unAssigned");

		System.out.println(" assigned sifhts ->  " + shiftAssignments == null ? 0 : shiftAssignments.size());
		System.out.println(" assigned sifhts -> " + unAssignedshift == null ?  0 :  unAssignedshift.size());
		
		for (Iterator iterator = shiftAssignments.iterator(); iterator.hasNext();) {
			ShiftAssignment assigment  = (ShiftAssignment) iterator.next();
			Shift s = assigment.getShift();
			Employee emp = assigment.getEmployee();
			System.out.println(s.getTimeslot().getStartTime() + " - " + s.getTimeslot().getEndTime());
			System.out.println(emp.getId() + " - " + emp.getName());
			
		}

			
		logger.close();
}
	
}
