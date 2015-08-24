
package genericCheckpointing.driver;

import genericCheckpointing.util.ProxyCreator;

// import the other types used in this file

public class Driver {
    
    public static void main(String[] args) {
	
	// FIXME: read the value of NUM_OF_OBJECTS from the command line
	// FIXME: read the value of checkpointFile from the command line
	
	ProxyCreator pc = new ProxyCreator();
	
	// create an instance of StoreRestoreHandler (which implements
	// the InvocationHandler
	
	// create a proxy
	StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(
								 new Class[] {
								     StoreI.class, RestoreI.class
								 }, 
								 new StoreRestoreHandler()
								 );
		
	// FIXME: invoke a method on the DJSONHandler instance to set the file name for checkpointFile and open the file


	SerializableObject myErecord;
	SerializableObject mySrecord;
	
	for (int i=0; i<NUM_OF_OBJECTS; i++) {
	    myErecord = new EmployeeRecord(int i);
	    mySrecord = new StudentRecord(int i);

	    // FIXME: store myErecord in a data structure
	    // FXIEM: store mySrecord in a data structure
	    ((StoreI) cpointRef).writeDJSON(myErecord, "djson");
	    ((StoreI) cpointRef).writeDJSON(mySrecord, "djson");

	}

	SerializableObject myRecordRet;

	for (int j=0; i<2*NUM_OF_OBJECTS; j++) {

	    myRecordRet = ((StoreI) cpointRef).readDJSON("djson");
	    // FIXME: store myRecordRet in a data structure
	}

	// FIXME: invoke a method on the DJSONHandler instance to close the file

	// FIXME: compare and confirm that the serialized and deserialzed objects are equal
    
    }
}
