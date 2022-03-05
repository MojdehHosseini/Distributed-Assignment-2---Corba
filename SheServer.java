/**
 * Developed to present CORBA usage for Distributes Systems (COMP 6231) course.
 * Developed by Seyedehmojdeh Haghighat Hosseini
 *
 * @author S.mojdeh Haghighat Hosseini
 */

import DAMSApp.DAMS;
import DAMSApp.*;
import DAMSApp.DAMSHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;



import java.util.Properties;

public class SheServer {
    public static void main(String args[]) {
        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            props.put("org.omg.CORBA.ORBInitialPort", "8050");
            String[] newArgs = new String[0];
            // create and initialize the ORB
            ORB orb = ORB.init(newArgs, props);
            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            SheImpl additionImpl = new SheImpl();
            additionImpl.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(additionImpl);
            DAMS href = DAMSHelper.narrow(ref);
		

            // get the root naming context
            // NameService invokes the name service
            org.omg.CORBA.Object objRef = orb.string_to_object("corbaloc::localhost:8050/NameService"); // InvalidName
            // Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            // bind the Object Reference in Naming
             String name = "she";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            

            System.out.println("Sherbrock ready and waiting ...");



            // wait for invocations from clients
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("DASherbrockMS Exiting ...");
    }
}
