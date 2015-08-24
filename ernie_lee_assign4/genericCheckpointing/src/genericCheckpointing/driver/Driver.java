
package genericCheckpointing.driver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import genericCheckpointing.util.ProxyCreator;
import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.djsonStoreRestore.StoreRestoreHandler;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.EmployeeRecord;
import genericCheckpointing.util.StudentRecord;
import genericCheckpointing.util.SerializableList;


//import the other types used in this file

public class Driver {
 
	
	/**
	 * Main driver method for the program
	 * 
	 * @param args
	 */
 public static void main(String[] args) {
 	
	// FIXME: read the value of NUM_OF_OBJECTS from the command line
	// FIXME: read the value of checkpointFile from the command line
 	int NUM_OF_OBJECTS = 0;
	String checkpointfile = "";
	
	try{
	
		if(args.length != 2)
			System.exit(0);
			
		NUM_OF_OBJECTS = Integer.parseInt(args[0]);
		checkpointfile = args[1];
		
 }catch(ArrayIndexOutOfBoundsException e){
 	e.printStackTrace();
 	System.exit(0);
 }
	
	ProxyCreator pc = new ProxyCreator();
	
	// create an instance of StoreRestoreHandler (which implements
	// the InvocationHandler
	StoreRestoreHandler handler = new StoreRestoreHandler(); 
	
	// create a proxy
		StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(
									 new Class[] {
									     StoreI.class, RestoreI.class
									 }, 
									 handler
									 );
		
	// FIXME: invoke a method on the DJSONHandler instance to set the file name for checkpointFile and open the file
	try {
		handler.openFileToWrite(checkpointfile);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		System.exit(0);
	}
		
	SerializableObject myErecord;
	SerializableObject mySrecord;
	
	List<SerializableObject> eRecordList = new SerializableList();
	List<SerializableObject> sRecordList = new SerializableList();
	
	for (int i=0; i<NUM_OF_OBJECTS; i++) {
	    myErecord = new EmployeeRecord(i);
	    mySrecord = new StudentRecord(i);
	    
	    // FIXME: store myErecord in a data structure
	    // FIXME: store mySrecord in a data structure
	    eRecordList.add(myErecord);
	    sRecordList.add(mySrecord);
	    
	    ((StoreI) cpointRef).writeDJSON(myErecord, "djson");
	    ((StoreI) cpointRef).writeDJSON(mySrecord, "djson");

	}
	
	handler.closeFile();
	handler.openFileToRead(checkpointfile);	

	SerializableObject myRecordRet;
	List<SerializableObject> eList = new SerializableList();
	List<SerializableObject> sList = new SerializableList();

	for (int j=0; j<2*NUM_OF_OBJECTS; j++) {

	    myRecordRet = ((RestoreI) cpointRef).readDJSON("djson");
	    // FIXME: store myRecordRet in a data structure
	    	
	    if(myRecordRet.getClass().getName().endsWith("EmployeeRecord"))
			eList.add(myRecordRet);
	
	    if(myRecordRet.getClass().getName().endsWith("StudentRecord"))
			sList.add(myRecordRet);
	}

	// FIXME: invoke a method on the DJSONHandler instance to close the file
	handler.closeFile();
	
	// FIXME: compare and confirm that the serialized and deserialzed objects are equal
 	System.out.println("EmployeeRecord objects are equal: " + eList.equals(eRecordList));
 	System.out.println("StudentRecord objects are equal: " + sList.equals(sRecordList));
 	
	
 }
}
