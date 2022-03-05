/**
 * Developed to present CORBA usage for Distributes Systems (COMP 6231) course.
 * Developed by Seyedehmojdeh Haghighat Hosseini
 *
 * @author S.mojdeh Haghighat Hosseini
 */

import DAMSApp.DAMS;
import DAMSApp.*;
import DAMSApp.DAMSHelper;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.jacorb.imr.Admin;
import org.omg.CORBA.*;
import java.util.Properties;

import java.util.Scanner;


public class DAMSClient {
    static DAMS dams;
    static String ID,PatientID,appointmentID,AdminID,appointmentType,registryURL;
    static Short quantity;


  

    public static void main(String args[]) 
    {
        try {
            //  create and initialize the ORB
            ORB orb = ORB.init(args, null);
            // get the root naming context
            			// -ORBInitialPort 1050 -ORBInitialHost localhost
         
            org.omg.CORBA.Object objRef = orb.string_to_object("corbaloc::localhost:8050/NameService");
            // Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // ServerObjectInterface obj = (ServerObjectInterface) ServerObjectInterfaceHelper.narrow(ncRef.resolve_str(serverPort));

            
            
            
            Scanner kbd = new Scanner(System.in);
            
            System.out.println("Enter your ID : ");
            ID = kbd.next();

            String serverPort = ChooseServerport(ID);
            System.out.println("server port: " + serverPort);

            // resolve the Object Reference in Naming
            DAMS dams = DAMSHelper.narrow(ncRef.resolve_str(serverPort));

            System.out.println("Obtained a handle on server object: " + dams);

            //// Admin Client
            if(ID.substring(3,4).equals("A"))
              {
                  AdminID=ID;
                  Scanner sc1 = new Scanner(System.in);
                  System.out.println("1.) Add Appointment");
                  System.out.println("2.) removeAppointment");
                  System.out.println("3.) listAppointmentAvailability");

                  System.out.println("Enter your choice : ");
                  int choice;
                  choice = sc1.nextInt();
                    
              
                  if(choice==1)
                  {   

                  
                      System.out.println("Enter AppintmentID : ");
                      appointmentID = kbd.next();

                    
                      System.out.println("Enter appointmentType: ");
                      appointmentType = kbd.next();
                      
                      System.out.println("Enter quantity : ");
                      quantity = kbd.nextShort();

                      System.out.println(dams.addAppointment(AdminID, appointmentID, appointmentType, quantity));
                      int a = dams.addAppointment(AdminID, appointmentID, appointmentType, quantity);
                      
                      if(a==2)
                      {
                        //logf.log(Level.INFO, "{0} appointemnt added : {1} {2} with quantity : {3}", new Object[]{AdminID, appointmentID, appointmentType, quantity});
                        System.out.println(" Added appointment : " + appointmentID + " "+ appointmentType + " with quantity : " + quantity);
                        }
                      if(a==1)
                      {
                        //logf.info("Not authorized");
                        System.out.println("Not authorized"+a);
                      }
                            
                      

                  }
                  if(choice==2)
                  {   
                      
                      System.out.println("Enter appointmentID : ");
                      appointmentID = sc1.next();
                      
                    
                      System.out.println("Enter appointmentType: ");
                      sc1.nextLine();
                      appointmentType = sc1.nextLine();
                    
                  
                      int a = dams.removeAppointment(AdminID, appointmentID, appointmentType);
                      
                    if(a==1)
                      {
                        //logf.log(Level.INFO, "{0} removed the appointemnt : {1} {2} ", new Object[]{AdminID, appointmentID, appointmentType});
                        System.out.println(AdminID+ "removed appointment : " + appointmentID + appointmentType);
                        }
                      if (a==2){

                        //logf.log(Level.INFO, "{0} wants to removed the appointemnt: {1} {2} but it was not exist ", new Object[]{AdminID, appointmentID, appointmentType});
                        System.out.println(" it is not exist");
                      }
                      else 
                      {
                        //logf.info("Not authorized");
                        System.out.println("Not authorized"+a);
                      }
          
                  }

            
                dams.shutdown();
              } 


            // Patient Client
            if(ID.substring(3,4).equals("P"))
            {

              PatientID=ID;
              Scanner sc1 = new Scanner(System.in);
              System.out.println("1.) book Appointment");
              System.out.println("2.) get AppointmentSchedule");
              System.out.println("3.) cancel Appointment");
  
              System.out.println("Enter your choice : ");
              int choice;
              choice = sc1.nextInt();
              
           
                  
                
  
              if(choice==1)
              {  //bookAppointment (patientID, appointmentID, appointmentType)
                  System.out.println("Enter appointmentID : ");
                  appointmentID = sc1.next();
    
                
                  System.out.println("Enter appointmentType: ");
                  sc1.nextLine();
                  appointmentType = sc1.nextLine();
                
              
                  int a = dams.bookAppointment(PatientID, appointmentID, appointmentType);
                  System.out.println("a"+a);
                  if(a==1)
                  {
                    // logf.log(Level.INFO, "{0} reserved the appointemnt : {1} {2} ", new Object[]{PatientID, appointmentID, appointmentType});
                    System.out.println(" Reserved appointment : " + appointmentID + " "+ appointmentType + " for : " + PatientID);
                    }
                  if (a==3){
                    // logf.info("This patient has alrady reserved 3 appointmennts from other cities");
                    System.out.println("You can only book at most 3 appointments from other cities");
                  }
                  else 
                  {
                    // logf.info("Not authorized");
                    System.out.println("Not authorized"+a);
                  }
              }
  
              if(choice==2)
              {
                System.out.println("Enter appointmentID : ");
                appointmentID = sc1.next();
        
                int a = dams.cancelAppointment(PatientID, appointmentID);
                  
                if(a==1)
                  {
                    // logf.log(Level.INFO, "{0} canceled the appointemnt : {1}  ", new Object[]{PatientID, appointmentID});
                    System.out.println(PatientID+ "canceled appointment : " + appointmentID );
                    }
                if (a==2){
    
                    // logf.log(Level.INFO, "{0} wants to cancel the appointemnt: {1}  but he/she could not ", new Object[]{PatientID, appointmentID});
                    System.out.println(" The patient was not permitted to cancel the appointment");
                  }
                if(a==0) 
                  {
                    // logf.info("Not authorized");
                    System.out.println("Not authorized");
                  }
              }
      
              if(choice==3)
              {
                  System.out.println("Enter appointmentID : ");
                  appointmentID = sc1.next();
    
                  
                        int a = dams.cancelAppointment(PatientID, appointmentID);
                        
                      if(a==1)
                        {
                          // logf.log(Level.INFO, "{0} canceled the appointemnt : {1}  ", new Object[]{PatientID, appointmentID});
                          System.out.println(PatientID+ "canceled appointment : " + appointmentID );
                        }
                        if (a==2)
                        {
    
                          // logf.log(Level.INFO, "{0} wants to cancel the appointemnt: {1}  but he/she could not ", new Object[]{PatientID, appointmentID});
                          System.out.println(" The patient was not permitted to cancel the appointment");
                        }
                        if(a==0)
                        {
                          // logf.info("Not authorized");
                          System.out.println("Not authorized"+a);
                        }
                        
                  
              }

            }
          }
          catch (Exception e) 
          {
              System.out.println("ERROR : " + e);
              e.printStackTrace(System.out);
          }
            
      }
          

    private static String ChooseServerport(String ID) {
      String serverPort="1";
      String serverDirection = ID.substring(0, Math.min(ID.length(), 3));
      if(serverDirection.equals("QUE") || serverDirection.equals("que")) {
        serverPort = "que";
      }else if(serverDirection.equals("SHE") || serverDirection.equals("she")) {
        serverPort = "she";
      }else if(serverDirection.equals("MON") || serverDirection.equals("mon")) {
        serverPort = "mon";
      }else {
        System.out.println("This is an invalid request. Please check your username");
      }
      return serverPort;
    }

}