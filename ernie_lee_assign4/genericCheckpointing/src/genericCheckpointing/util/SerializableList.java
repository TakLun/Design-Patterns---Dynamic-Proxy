package genericCheckpointing.util;

import java.util.ArrayList;

public class SerializableList extends ArrayList<SerializableObject>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1264701146117255421L;
	
	/**
	 * Overrides ArrayList equals() method
	 * Compares contents of two lists
	 * 
	 * @param o
	 */
	@Override
	public boolean equals(Object o){
		
		boolean isEqual = true;
		
		if(((ArrayList<?>)o).get(0) instanceof EmployeeRecord){
		
			for(int i=0;i<((ArrayList<?>)o).size();i++){
			
				if(((EmployeeRecord)(this.get(i))).get_ii() != ((EmployeeRecord)((ArrayList<?>)o).get(i)).get_ii() ){
					isEqual = false;
					break;	
				}
				
				if(((EmployeeRecord)(this.get(i))).get_ff() != ((EmployeeRecord)((ArrayList<?>)o).get(i)).get_ff() ){
					isEqual = false;
					break;	
				}
				
				if(((EmployeeRecord)(this.get(i))).get_dd() != ((EmployeeRecord)((ArrayList<?>)o).get(i)).get_ll() ){
					isEqual = false;
					break;	
				}
				
				if(((EmployeeRecord)(this.get(i))).get_ll() != ((EmployeeRecord)((ArrayList<?>)o).get(i)).get_ll() ){
					isEqual = false;
					break;	
				}
			}	
					
		}else if(((ArrayList<?>)o).get(0) instanceof StudentRecord){
		
			for(int i=0;i<((ArrayList<?>)o).size();i++){
			
				if(((StudentRecord)(this.get(i))).get_ii() != ((StudentRecord)((ArrayList<?>)o).get(i)).get_ii() ){
					isEqual = false;
					break;	
				}
				
				if(((StudentRecord)(this.get(i))).get_ss() != ((StudentRecord)((ArrayList<?>)o).get(i)).get_ss() ){
					isEqual = false;
					break;	
				}
				
				if(((StudentRecord)(this.get(i))).get_bb() != ((StudentRecord)((ArrayList<?>)o).get(i)).get_bb() ){
					isEqual = false;
					break;	
				}
			}			
		}
		
		
		
		return isEqual;
	}
	
	/**
	 * Overrides ArrayList hashCode() method
	 * 
	 */
	@Override 
	public int hashCode(){
		return super.hashCode();
	}

}

