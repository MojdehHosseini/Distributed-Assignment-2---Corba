/**
 * Developed to present CORBA usage for Distributes Systems (COMP 6231) course.
 * Developed by Brijesh Lakkad
 *
 * @author brijeshlakkad
 */

import DAMSApp.DAMS;
import DAMSApp.*;
import DAMSApp.DAMSHelper;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Properties;

import java.util.Scanner;


public class DAMSClient {
    static DAMS dams;
    static String appointmentID,AdminID,appointmentType,registryURL;
    static Short quantity;


  

    public static void main(String args[]) {
        try {
            // /// create and initialize the ORB
            // Properties props = new Properties();
            // props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            // props.put("org.omg.CORBA.ORBInitialPort", "8050");
            // String[] newArgs = new String[0];
            // // create and initialize the ORB
            // ORB orb = ORB.init(newArgs, props);
            ORB orb = ORB.init(args, null);
            // get the root naming context
            org.omg.CORBA.Object objRef = orb.string_to_object("corbaloc::localhost:8050/NameService");
            // Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // resolve the Object Reference in Naming
            
       
            String name = "DAMS";
            dams = DAMSHelper.narrow(ncRef.resolve_str(name));
            System.out.println("Obtained a handle on server object: " + dams);

            Scanner kbd = new Scanner(System.in);
            
            System.out.println("Enter AdminID : ");
            AdminID = kbd.next();

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
                System.out.println("daaaammmmssss   :   "+dams.addAppointment(AdminID, appointmentID, appointmentType, quantity));
                System.out.println(dams.addAppointment(AdminID, appointmentID, appointmentType, quantity));
                String a = dams.addAppointment(AdminID, appointmentID, appointmentType, quantity);
                System.out.println("a"+a);
                if(a=="Y")
                {
                  //logf.log(Level.INFO, "{0} appointemnt added : {1} {2} with quantity : {3}", new Object[]{AdminID, appointmentID, appointmentType, quantity});
                  System.out.println(" Added appointment : " + appointmentID + " "+ appointmentType + " with quantity : " + quantity);
                  }
                else 
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
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}