package genericCheckpointing.util;

public class StudentRecord extends SerializableObject{

    private int ii;
    private short ss;
    private boolean bb;
    
    /**
     * empty constructor needed for reflection
     */
    // empty constructor is needed for reflection
    public StudentRecord() {
	// set default values for data members
    }

    /**
     * initializes the ii variable
     * @param iiIn
     */
    public StudentRecord(int iiIn) {
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
     * Sets ss variable
     * @param ssIn
     */
    public void set_ss(short ssIn) {
    	ss = ssIn;
    }

    /**
     * sets  bb variable
     * @param bbIn
     */
    public void set_bb(boolean bbIn) {
    	bb = bbIn;
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
     * Returns ss of type short
     * @return ss
     */
    public short get_ss() {
    	return ss;
    }
    
    /**
     * Returns bb of type boolean
     * @return bb
     */
    public boolean get_bb() {
    	return bb;
    }

    // FIXME: rest of the get_X methods
}
