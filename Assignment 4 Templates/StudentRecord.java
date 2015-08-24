package genericCheckpointing.util;

public class StudentRecord extends SerializableObject {

    private int ii;
    private short ss;
    private boolean bb;
    
    // empty constructor is needed for reflection
    public StudentRecord() {
	// set default values for data members
    }



    public StudentRecord(int iIn) {
	i = iIn;
	// set default values for data members
    }

    public void set_ii(int iiiIn) {
	ii = iiIn;
    }

    // FIXME: rest of the set_X methods

    public int get_ii() {
	return ii;
    }

    // FIXME: rest of the get_X methods



}
