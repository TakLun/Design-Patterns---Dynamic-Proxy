package genericCheckpointing.util;

public class EmployeeRecord extends SerializableObject{
     private int ii;
    private float ff;
    private double dd;
    private long ll;
    
    /**
     * empty constructor needed for reflection
     */
    // empty constructor is needed for reflection
    public EmployeeRecord() {
	// set default values for data members
    	
    }

    /**
     * initializes the ii variable
     * @param iiIn
     */
    public EmployeeRecord(int iiIn) {
    	ii = iiIn;
	// set default values for data members
    }

    /**
     * Sets ii variable
     * @param iiIn
     */
    public void set_ii(int iiIn) {
    	ii = iiIn;
    }

    /**
     * Sets ff variable
     * @param ffIn
     */
    public void set_ff(float ffIn) {
    	ff = ffIn;
    }
    
    /**
     * Sets dd Variable
     * @param ddIn
     */
    public void set_dd(double ddIn) {
    	dd = ddIn;
    }
    
    /**
     * sets ll variable
     * @param llIn
     */
    public void set_ll(long llIn) {
    	ll = llIn;
    }
    // FIXME: rest of the set_X methods

    /**
     * Returns ii of type int
     * @return ii
     */
    public int get_ii() {
    	return ii;
    }

    /**
     * Returns ff of type float
     * @return ff
     */
    public float get_ff() {
    	return ff;
    }

    /**
     * Returns dd of type double
     * @return dd
     */
    public double get_dd() {
    	return dd;
    }

    /**
     * Returns ll of type long
     * @return ll
     */
    public long get_ll() {
    	return ll;
    }

    // FIXME: rest of the get_X methods
}
