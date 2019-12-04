package com.brms.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.server.api.exception.KieServicesException;
import org.kie.server.api.exception.KieServicesHttpException;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.instance.ScoreWrapper;
import org.kie.server.api.model.instance.SolverInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.SolverServicesClient;
import org.kie.server.client.impl.KieServicesConfigurationImpl;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import employeerostering.employeerostering.DayOffRequest;
import employeerostering.employeerostering.Employee;
import employeerostering.employeerostering.EmployeeRoster;
import employeerostering.employeerostering.Shift;
import employeerostering.employeerostering.ShiftAssignment;
import employeerostering.employeerostering.Skill;
import employeerostering.employeerostering.Timeslot;

public class EmployeeRosterTest {
	
	
	
	public static EmployeeRoster getSolution() {
		 Skill skill = new Skill("chess","1",1);
	        Skill skill2 = new Skill("chess1","1",1);
	      //DayOffRequest dayOffRequest =new DayOffRequest();
	        //DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "F",1);
	        DayOffRequest dayOffRequest = new DayOffRequest( null,LocalDate.of(2017,1,1), "H",LocalTime.of(16,0,0),LocalTime.of(18,0,0) );

	        Employee employee = new Employee("Jey1",
	                                         Arrays.asList(skill));
	        Employee employee2 = new Employee("Jey2",
	                Arrays.asList(skill2),dayOffRequest,2);
	        Employee employee3 = new Employee("Jey3",
	                Arrays.asList(skill2));
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
	        Shift shift = new Shift(timeslot,skill);
	        Shift shift2 = new Shift(timeslot2,skill2);
	        

	       //DayOffRequest dayOffRequest = new DayOffRequest(employee, LocalDate.of(2017,1,1), "H",LocalTime.of(15,0,0),LocalTime.of(19,0,0) );

	        
	        ShiftAssignment shiftAssignment = new ShiftAssignment();
	        shiftAssignment.setShift(shift);
	        ShiftAssignment shiftAssignment2 = new ShiftAssignment();
	        shiftAssignment2.setShift(shift2);
	        
	        return new EmployeeRoster(Arrays.asList(employee,employee2,employee3)
	        		, Arrays.asList(shift,shift2), 
	        		Arrays.asList(skill,skill2)
	        		, Arrays.asList(timeslot,timeslot2),
	        		new ArrayList(),Arrays.asList(shiftAssignment,shiftAssignment2), null);
	        		//Arrays.asList(dayOffRequest),Arrays.asList(shiftAssignment,shiftAssignment2), null);
    }
	public static void main(String[] a){
		
		

		String SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
	    String USERNAME = "rhdmAdmin";
	    String PASSWORD = "jboss123$";
	   // int CLIENT_TIMEOUT = 30000;
	    String CONTAINER_ID = "employeerostering";
	    String SOLVER_CONFIG_XML_PATH = "employeerostering/employeerostering/EmployeeRosteringSolverConfig.solver.xml";
	    String SOLVER_ID = "employeeRosterSolver";
		  //KieServicesConfiguration kieServicesConfiguration = new KieServicesConfigurationImpl(SERVER_URL, USERNAME, PASSWORD, CLIENT_TIMEOUT);
	    KieServicesConfiguration kieServicesConfiguration = new KieServicesConfigurationImpl(SERVER_URL, USERNAME, PASSWORD);
		  kieServicesConfiguration.setTimeout(300000);
		  Set<Class<?>> extraClassList = new HashSet<Class<?>>();
		    extraClassList.add(EmployeeRoster.class);
		    extraClassList.add(Employee.class);
		    extraClassList.add(Shift.class);
		    extraClassList.add(ShiftAssignment.class);
		    extraClassList.add(Skill.class);
		    extraClassList.add(DayOffRequest.class);
		    extraClassList.add(Timeslot.class);

		   kieServicesConfiguration.addExtraClasses(extraClassList);
		  kieServicesConfiguration.setMarshallingFormat(MarshallingFormat.JSON);
		  KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfiguration);
		  ReleaseId releaseId = new ReleaseId("employeerostering", "employeerostering", "1.0.8");
		  KieContainerResource containerResource = new KieContainerResource(CONTAINER_ID, releaseId);
		  kieServicesClient.createContainer(CONTAINER_ID, containerResource);
		  
		  SolverServicesClient solverClient = kieServicesClient.getServicesClient(SolverServicesClient.class);
		  SolverInstance solverInstance = null;
		  try{
		  //create solver
		   solverInstance  =  solverClient.createSolver(CONTAINER_ID, SOLVER_ID, SOLVER_CONFIG_XML_PATH);
		
		  EmployeeRoster planningProblem = EmployeeRosterTest.getSolution();
		 
		  //start solver
		  solverClient.solvePlanningProblem(CONTAINER_ID, SOLVER_ID, planningProblem);
		  }catch (KieServicesException e) {
	           // KieServerAssert.assertResultContainsStringRegex(e.getMessage(),
                 //       ".*Solver .* on container .* is already executing.*");
			  //solverClient.disposeSolver(CONTAINER_ID,
                //      SOLVER_ID);
			 

		  }
		  
	      HardSoftScore score = null;
		  while ( !Thread.currentThread().isInterrupted()  ) {
			  
			  solverInstance = solverClient.getSolver(CONTAINER_ID, SOLVER_ID);
			 
			  //solution = solverInstance.getBestSolution();
			  if (solverInstance.getStatus() == SolverInstance.SolverStatus.SOLVING) {
                  // continue
                 // System.out.println("Still solving " + task);
              } else {
            	  solverInstance = solverClient.getSolverWithBestSolution(CONTAINER_ID, SOLVER_ID);
            	  
            	  EmployeeRoster employeeRoster = (EmployeeRoster)solverInstance.getBestSolution();                 
                  
            	  ScoreWrapper scoreWrapper = solverInstance.getScoreWrapper();

  	            	if (scoreWrapper.toScore() != null) {
  	            		score = (HardSoftScore) scoreWrapper.toScore();
  	            		System.out.println("is score initialized" + score.isSolutionInitialized());
  	            		System.out.println("score " + score.getHardScore());  	             
  	            		System.out.println(employeeRoster.getShiftAssignmentList().size());
  	            		System.out.println(employeeRoster.getScore());
  	            		for (Iterator iterator = employeeRoster.getShiftAssignmentList().iterator(); iterator.hasNext();) {
  	            			ShiftAssignment type = (ShiftAssignment) iterator.next();
  	            			System.out.println(type.getEmployee().getName() );
  	            		}
  	                
  	                solverClient.disposeSolver(CONTAINER_ID, SOLVER_ID);
    	            //done = true;
    	            break;
  	            }
  	            
        		
              }

	          /*  
	            // Wait until the solver finished initializing the solution
	            if (solution != null && score != null && score.isSolutionInitialized()) {
	                break;
	            }*/
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		 
	 
		  
		  


	}

}
