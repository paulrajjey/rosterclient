package com.brms.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.drools.core.command.runtime.rule.QueryCommand;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import com.mprocessdemo.mydemo.Customer;


//import citi.PRSruleengine.FieldRequest;

public class ProcessServiceClient {


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
       String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
        String user = "rhpamAdmin";
        //String serverUrl = "http://10.0.2.15:9080/kie-server/services/rest/server";
        //String user = "jpaulraj";
        String password = "jboss123$";
      // String containerId = "mydemo";
        String containerId = "mcdemoprocess";
        

       // String processId = "hiring";
       
        
        Set<Class<?>> classes = new HashSet<Class<?>>();
        //classes.add(User.class);
      //  classes.add(RiskLevel.class);
        Marshaller marshaller = MarshallerFactory.getMarshaller(classes,  MarshallingFormat.JSON, ProcessServiceClient.class.getClassLoader());
       // KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration(serverUrl, user, password);
        KieServicesClient  kieServicesClient = ProcessServiceClient.configure(serverUrl, user, password);
        
     
       // RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        Map<String , Object> param = new HashMap<String, Object>();
        List custmers = new ArrayList();
        Customer custom1 = new Customer();
        custom1.setAge(15);
        custom1.setName("test1");
        custom1.setRole("approval");
        
        Customer custom2 = new Customer();
        custom2.setAge(25);
        custom2.setName("test2");
        custom2.setRole("review");

        Customer custom3 = new Customer();
        custom3.setAge(15);
        custom3.setName("test3");
        custom3.setRole("approval");

        
        custmers.add(custom1);
        custmers.add(custom2);
        custmers.add(custom3);
        
        //param.put("customerList", custmers);
        
        param.put("customeId", "1002");
        
       /*Productdemo product = new Productdemo();
        product.setId("1001");
        product.setName("demo");
        product.setPrice(100);
        product.setState("WI");
        
        DemoTransaction tr = new DemoTransaction();
        tr.setName("Jey");
        tr.setTransId(1001);
        tr.setNumOfItems(10);
        tr.setState("WI");*/
       // processClient.getProcessDefinition("cloudservice", "cloudapproval.cloudservicecatalogApproval");
       // processClient.getServiceTaskDefinitions("cloudservice", "cloudapproval.cloudservicecatalogApproval");
        //param.put("name", "bpmsAdmin");
       // processClient.getProcess
        //Long id = processClient.startProcess("cloudservice", "cloudapproval.cloudservicecatalogApproval", param);
      //  Long id = processClient.startProcess(containerId, "redhatdemo.redhatdemoprocess", param);
        //Long id = processClient.startProcess(containerId, "mydemo.miprocess", param);
        Long id = processClient.startProcess(containerId, "mcdemoprocess.mcdemoprocess", param);
       
       System.out.println("Process statred " + id);
        
        
        System.out.println("Execution completed in " + (System.currentTimeMillis() - start));

    }
    
public static KieServicesClient configure(String url, String username, String password) {
		
		//default marshalling format is JAXB
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		
	
		
		//alternatives
		
		//config.setMarshallingFormat(MarshallingFormat.XSTREAM);
		config.setMarshallingFormat(MarshallingFormat.JSON);

	
		Set<Class<?>> allClasses = new HashSet<Class<?>>();
		//allClasses.add(com.highmark.domain.RiskLevel.class);
		/*allClasses.add(org.drools.core.command.runtime.rule.FireAllRulesCommand.class);
		allClasses.add(org.drools.core.command.runtime.rule.InsertObjectCommand.class);
		allClasses.add(org.drools.core.common.DefaultFactHandle.class);
		allClasses.add(org.drools.core.command.runtime.rule.GetObjectCommand.class);*/
		//config.addJaxbClasses(allClasses);
		return KieServicesFactory.newKieServicesClient(config);
		//
	}

}