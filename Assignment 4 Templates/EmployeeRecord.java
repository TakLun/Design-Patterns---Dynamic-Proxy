package genericCheckpointing.util;

public class EmployeeRecord extends SerializableObject {
    private int ii;
    private float ff;
    private double dd;
    private long ll;

    // empty constructor is needed for reflection
    public EmployeeRecord() {
	// set default values for data members
    }

    
    public EmployeeRecord(int iIn) {
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
