package com.brms.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.server.api.exception.KieServicesException;
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
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import employeerostering.employeerostering.DayOffRequest;
import employeerostering.employeerostering.Employee;
import employeerostering.employeerostering.EmployeeRoster;
import employeerostering.employeerostering.Shift;
import employeerostering.employeerostering.ShiftAssignment;
import employeerostering.employeerostering.Skill;
import employeerostering.employeerostering.Timeslot;

public class EmployeeRosterTest2 {
	
	
	
	public static EmployeeRoster getSolution() {
        Skill skill = new Skill("chess","1",1);
        Skill skill2 = new Skill("chess1","1",1);
        DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "H",LocalTime.of(16,0,0),LocalTime.of(18,0,0) ,1);
       //DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "H",LocalTime.of(15,0,0),LocalTime.of(17,0,0) );
       // DayOffRequest dayOffRequest = new DayOffRequest( LocalDate.of(2017,1,1), "F");
        

        Employee employee = new Employee("Jey1",
                Arrays.asList(skill),1);
        Employee employee2 = new Employee("Jey2",
        			Arrays.asList(skill2), dayOffRequest,2);
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
        Shift shift = new Shift(timeslot,skill);
        Shift shift2 = new Shift(timeslot2,skill2);
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
		
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId releaseId = new ReleaseId("employeerostering", "employeerostering", "1.0.10");
		KieContainer kieContainer = kieServices.newKieContainer(releaseId);
		kieContainer = kieServices.newKieContainer(releaseId);
        SolverFactory<EmployeeRoster> solverFactory = SolverFactory.createFromKieContainerXmlResource(kieContainer, "employeerostering/employeerostering/EmployeeRosteringSolverConfig.solver.xml");
        EmployeeRoster roster1 = EmployeeRosterTest2.getSolution();
        Solver<EmployeeRoster> solver = solverFactory.buildSolver();
        solver.addEventListener(new SolverEventListener<EmployeeRoster>() {
			
			public void bestSolutionChanged(BestSolutionChangedEvent<EmployeeRoster> arg0) {
				// TODO Auto-generated method stub
				System.out.println("new bset scorfe " + arg0.getNewBestScore());
				
			}
		});
        solver.solve(roster1);
        EmployeeRoster roster = solver.getBestSolution();
        System.out.println(roster.getShiftAssignmentList().size());
        System.out.println(roster.getScore());
        for (Iterator iterator = roster.getShiftAssignmentList().iterator(); iterator.hasNext();) {
			ShiftAssignment type = (ShiftAssignment) iterator.next();
			 System.out.println(type.getEmployee().getName() + " : " + type.getShift().getTimeslot().getStartTime() );

			
		}
	}

}
