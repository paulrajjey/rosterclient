package com.brms.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.core.command.runtime.rule.QueryCommand;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.mvel2.optimizers.impl.refl.nodes.ArrayLength;

import com.bell.belldemo.BellApp;
import com.idexx.idexxdemo.ValidationDomain;

import employeerostering.employeerostering.DayOffRequest;
import employeerostering.employeerostering.Employee;
import employeerostering.employeerostering.Shift;
import employeerostering.employeerostering.ShiftAssignment;
import employeerostering.employeerostering.Skill;
import employeerostering.employeerostering.Timeslot;



public class RoasterngRestClient {
	
	
	public static void main(String[] s){
		
		RoasterngRestClient el = new RoasterngRestClient();
		el.execute();
	        
	}
	public void execute(){
		String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
	    String user = "rhdmAdmin";
	     
	       String password = "jboss123$";
	       String containerId = "employeerostering";
	        KieServicesClient  kieServicesClient = RoasterngRestClient.configure(serverUrl, user, password);

		 RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
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
	        //System.out.println("\t######### Rules request"  + xStreamXml);
	   
	        //ServiceResponse<String> result = ruleClient.executeCommandsWithResults(containerId, batchCommand);
	        ServiceResponse<ExecutionResults> response  = ruleClient.executeCommandsWithResults(containerId, batchCommand);
	        
	        ExecutionResults actualData = response.getResult();
	        java.util.ArrayList shiftAssignments = (java.util.ArrayList) actualData.getValue("assignmnets");
	        
	       for (Object object : shiftAssignments){
	    	   ShiftAssignment assigment = (ShiftAssignment)object;
	    	   Shift s = assigment.getShift();
				Employee emp = assigment.getEmployee();
				System.out.println(s.getTimeslot().getStartTime() + " - " + s.getTimeslot().getEndTime());
				System.out.println(emp.getId() + " - " + emp.getName());
	       }
	        
	       /*for (Iterator iterator2 = shiftAssignments.iterator(); iterator2.hasNext();) {
	        	java.util.LinkedHashMap lsmap =  (java.util.LinkedHashMap) iterator2.next();
	        	Set ls = lsmap.entrySet();
	        	for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
					ShiftAssignment assigment = (ShiftAssignment) iterator.next();
					Shift s = assigment.getShift();
					Employee emp = assigment.getEmployee();
					System.out.println(s.getTimeslot().getStartTime() + " - " + s.getTimeslot().getEndTime());
					System.out.println(emp.getId() + " - " + emp.getName());
				}*/
	        	
			
				
			
	        
	      
	       
	       
	       
	}
	/*public void payload(){
		String serverUrl = "http://localhost:8080/kie-execution-server/services/rest/server";
	    String user = "rhdmAdmin";
	     
	       String password = "jboss123$";
	       String containerId = "dm7test";
	       KieServicesClient  kieServicesClient = Eligibility.configure(serverUrl, user, password);
	        RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
	        KieCommands commandsFactory =
	         		KieServices.Factory.get().getCommands();
	        EligibilityCriteria eligiblity = new EligibilityCriteria();
	        eligiblity.setCritrria("BMI");
	        eligiblity.setValue(151);
	        Command<?> insertEmp = commandsFactory.newInsert(eligiblity, "eligibilityResponse");
	        
	        Command<?> fireAllRules = commandsFactory.newFireAllRules();
	        Command<?> batchCommand =
	      			 commandsFactory.newBatchExecution(Arrays.asList(insertEmp,fireAllRules));
	        Marshaller marshaller = MarshallerFactory.getMarshaller( MarshallingFormat.JSON, getClass().getClassLoader() );
	        String result = marshaller.marshall( batchCommand );
	        //String result = BatchExecutionHelper.newXStreamMarshaller().toXML(batchCommand);
	      //  String result = BatchExecutionHelper.newJSonMarshaller().toXML(batchCommand);
	        		//newJSonMarshaller().toXML(batchCommand);

        
	}*/
public static KieServicesClient configure(String url, String username, String password) {
		
		//default marshalling format is JAXB
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		
	
		config.setMarshallingFormat(MarshallingFormat.JSON);
		//config.setMarshallingFormat(MarshallingFormat.JSON);

	
		
		return KieServicesFactory.newKieServicesClient(config);
		//
	}
}
