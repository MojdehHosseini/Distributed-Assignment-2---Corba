
import DAMSApp.DAMSPOA;

import java.io.IOException;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.FileHandler;

import org.omg.CORBA.ORB;

public class QueImpl extends DAMSPOA {
    private ORB orb;


   HashMap<String,Integer> innerDB = new HashMap<String,Integer>();
   HashMap<String, HashMap<String,Integer>> DB = new HashMap<String,HashMap<String,Integer>>();
   HashMap<String, ArrayList<String>> booked = new HashMap<String,ArrayList<String>>();




   Logger logf;
   FileHandler fh;

   public QueImpl() throws IOException {
//        logf = Logger.getLogger(this.getClass().getSimpleName());
//        fh = new FileHandler("/Users/mojdeh/Desktop/DSAssignment1/server/Implementation.log", true);
//        fh.setFormatter(new SimpleFormatter());
//        logf.addHandler(fh);
       DBconstruct();
    }


   public void DBconstruct() {

      
      //QUE Database
      innerDB.put("QUEA101122",0);
      innerDB.put("QUEM101122",10);
      innerDB.put("QUEE101122",2);
      DB.put("Surgeon", innerDB);

      innerDB.put("QUEA101122",3);
      innerDB.put("QUEM101122",0);
      innerDB.put("QUEE101122",27);
      DB.put("Dental", innerDB);

      innerDB.put("QUEA101122",4);
      innerDB.put("QUEM101122",9);
      innerDB.put("QUEE101122",2);
      DB.put("Physician", innerDB);

      ArrayList<String> appo = new ArrayList<String>();
      appo.add("QUEE101122");
      booked.put("QUEP1245",appo);
      booked.put("QUEP1345",appo);
      booked.put("QUEP1545",appo);
      booked.put("QUEP1595",appo);

      
   }

    public void setORB(ORB orb) {
        this.orb = orb;
    }
 
  
    //listAppointmentAvailability (appointmentType):
    @Override
    public int listAppointmentAvailability (String adminID, String appointmentType)  {
    //    if (adminID.substring(3,4).equals("A")){
          
    //        //printH();
    //      if (((HashMap<String,Integer>)DB.get(appointmentType)).containsKey(appointmentID)) {
    //        //  System.out.println("inside");
    //          synchronized (this) {
                 
    //              if (((HashMap<String,Integer>)DB.get(appointmentType)).get(appointmentID) >0) {
                    
    //                ((HashMap<String,Integer>)DB.get(appointmentType)).remove(appointmentID);
    //                System.out.println("the"+appointmentID+"of"+appointmentType + "has removed");
    //                String s3 = "";
    //                s3 = "the"+appointmentID+"of"+appointmentType + "has removed";
    //                //logf.info(s3);
    //                return 1; // 1 is item cleared with some quantity
    //                  } 
    //                }
    //             }
    //       else{
    //             System.out.println("it is not exist");
    //             return 2;
 
    //             }}
          return 0;
       
             
 
    } //end class
 
    @Override
    public int bookAppointment (String patientID, String appointmentID, String appointmentType)  {
      
         int out=0;
         short result = 0;
           
         if (((HashMap<String,Integer>)DB.get(appointmentType)).containsKey(appointmentID)) {
          
             synchronized (this) {
                 
                 if (((HashMap<String,Integer>)DB.get(appointmentType)).get(appointmentID) >0) 
                 {
                    
                   int c = ((HashMap<String,Integer>)DB.get(appointmentType)).get(appointmentID);
                   c--;
                   ((HashMap<String,Integer>)DB.get(appointmentType)).put(appointmentID,c);
                  
               
                      
                   if (!booked.containsKey(patientID))
                   {
                      System.out.println("Its your first appointement");
                      ArrayList<String> ap = new ArrayList<String>();
                      ap.add(appointmentID);
                      booked.put(patientID, ap);
                      String s3 = "";
                      s3 = "the "+appointmentID+" for "+patientID + " is reserved.";
                      //logf.info(s3);
                      result=1;
 
                   }
                   
                   else
                   {  
                      ArrayList<String> b = new ArrayList<String>();
                      b = booked.get(patientID);
                      //Count other cities oppointments
                      for(String bookedAppo : b) {
                         System.out.println(bookedAppo); 
                         if (!patientID.substring(0,3).equals(bookedAppo.substring(0,3))){
                            out++;
 
                         }
                      } 
                      //Checking that the patient only book 3 appointment from other cities
                      if (out==3 & !patientID.substring(0,3).equals(appointmentID.substring(0,3)))
                      {
 
                         System.out.println("You can not reserve more than 3 appointments from other cities");
                         String s3 = "";
                         s3 = "The "+patientID + " could not reseerved the " + appointmentID+"because already reserved more than 3";
                         //logf.info(s3);
                         result=3;
                      }
              
                      else
                      {
                         String[] newappointment = {appointmentID};
                         b.addAll(Arrays.asList(newappointment));
                         booked.put(patientID, b);
                         System.out.println("the "+appointmentID+" for "+patientID + " is reserved.");
                         String s3 = "";
                         s3 = "the "+appointmentID+" for "+patientID + " is reserved.";
                         //logf.info(s3);
                         result=1; // 1 is the appointment is booked with this patient
                      }
                      
                   }
 
              
            
                } 
                else{
                   System.out.println("There is no free appoointment");
                   String s3 = "";
                   s3 = "The "+patientID + " could not reseerved the " + appointmentID + "becuase it was full";
                   //logf.info(s3);
                   result=2;
 
                }
             }
          }
          else{
                System.out.println("it is not exist");
                String s3 = "";
                s3 = "The "+patientID + " could not reseerved the " + appointmentID+"because this appointments was not exist";
                //logf.info(s3);
                result=2;
 
                }
       
          return result;
    } //end class

    //getAppointmentSchedule (patientID):
 
    //cancelAppointment (patientID, appointmentID)
    @Override
    public int cancelAppointment (String patientID, String appointmentID) {
        short result=0;
          if(booked.containsKey(patientID)){
             synchronized (this) {
                if(((ArrayList<String>)booked.get(patientID)).contains(appointmentID)){
 
                   ((ArrayList<String>)booked.get(patientID)).remove(appointmentID);
                   System.out.println("the "+appointmentID+" for "+patientID + " is canceled.");
                   String s3 = "";
                   s3 = "the "+appointmentID+" for "+patientID + " is canceled.";
                   //logf.info(s3);
                   result= 1;
          
                }
                else{
 
                   System.out.println("You did not book this appointment before");
                   String s3 = "";
                   s3 = "The "+patientID + " could not cancel the " + appointmentID;
                   //logf.info(s3);
                   result=2;
 
                }  
 
             }
          }
       else{
 
             System.out.println("You did not book any appointment");
             String s3 = "";
             s3 = "The "+patientID + " does n't have any appointments " + appointmentID;
             //logf.info(s3);
             result= 2;
  
       }
 
       return result;
    } //end class


   @Override
   public int addAppointment(String adminID, String appointmentID, String appointmentType, int capacity) {
      int result=0;
          
      if (("A").equals(adminID.substring(3,4)))
      {
        
            if (((HashMap<String,Integer>)DB.get(appointmentType)).containsKey(appointmentID)) {
               
               String c = ((HashMap<String, Integer>)DB.get(appointmentType)).get(appointmentID).toString();
               System.out.println("This appointment exists, the capacity is: "+ c );
               result = 1;

            }

            else{
               int ca=(int)(capacity);
               System.out.println("you can add this to the appointments");
               innerDB.put(appointmentID,ca);
               DB.put(appointmentType,innerDB);
               System.out.println("The appointment is added");
               result = 2;

            }


      }
      return result;
      
   }

   @Override
   public int removeAppointment(String adminID, String appointmentID, String appointmentType) {
      short result=0;

      if (adminID.substring(3,4).equals("A")){
   
       if (((HashMap<String,Integer>)DB.get(appointmentType)).containsKey(appointmentID)) {
         
           synchronized (this) {
               
               if (((HashMap<String,Integer>)DB.get(appointmentType)).get(appointmentID) >0) {
                  
                 ((HashMap<String,Integer>)DB.get(appointmentType)).remove(appointmentID);
                 System.out.println("the"+appointmentID+"of"+appointmentType + "has removed");
                 String s3 = "";
                 s3 = "the"+appointmentID+"of"+appointmentType + "has removed";
                 //logf.info(s3);
                 result=1; 
                   } 
                 }
              }
        else{
              System.out.println("it is not exist");
              result= 2;

              }
         }
        return result;
        
   }

   @Override
   public void shutdown() {
       orb.shutdown(true);
   }

  
  
}
