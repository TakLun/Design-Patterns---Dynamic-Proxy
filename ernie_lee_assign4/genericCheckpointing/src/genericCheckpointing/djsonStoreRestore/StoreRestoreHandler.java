package genericCheckpointing.djsonStoreRestore;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.util.SerializableObject;

public class StoreRestoreHandler implements InvocationHandler, StoreI, RestoreI{

	private String checkpointFile;
	
	private File file;
	private BufferedReader reader;
	private PrintWriter writer;
	
	/**
	 * Default Constructor for StoreRestoreHandler
	 */
	public StoreRestoreHandler(){
		checkpointFile = "";
		file = null;
		
		reader = null;
		writer = null;
	}
	
	/**
	 * Opens file for the program to write object data into
	 * 
	 * @param checkpointfile
	 * @throws FileNotFoundException
	 */
	public void openFileToWrite(String checkpointfile) throws FileNotFoundException{
		checkpointFile = checkpointfile;
		
		try {
			
			file = new File(checkpointFile);
			writer = new PrintWriter(file);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Opens File for the program to read object data from
	 * 
	 * @param checkpointfile
	 */
	public void openFileToRead(String checkpointfile){
		checkpointFile = checkpointfile;
		
		try {
			
			file = new File(checkpointFile);
			reader = new BufferedReader(new FileReader(file));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	
	}
	
	/**
	 * Closes any readers or writers that are still open in the programs
	 */
	public void closeFile(){
		
		try{
			if(reader != null){
				reader.close();				
			}
			
			if(writer != null){
				writer.close();
			}
		}catch (IOException e){
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	/**
	 * Overridden Invoke method for the proxy to call.
	 * Determines what method is being called and calls the method given in the parameters
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	
		String methodName = method.getName();
		Class<?> className = method.getDeclaringClass();
		
		/*
		System.out.print("Class name is " + className.getName() + 
				" and calls its method " + methodName + " with arguments: ");
		for(int i=0;i<args.length;i++)
			System.out.print(args[i].toString() + ": ");
		System.out.println();
		*/
		
		if(className.equals(StoreI.class) && methodName.equals("writeDJSON")
				&& (args[0] instanceof SerializableObject)){
			
			//System.out.println("Method writeDJSON is called");
			writeDJSON((SerializableObject)args[0], args[1].toString());
			
		}else if(className.equals(RestoreI.class) && methodName.equals("readDJSON")){
			
			//System.out.println("Method readDJSON is called");
			return readDJSON(args[0].toString());
		}
		
		return null;
	
	}

	/**
	 * Given the wire format, read the data from the file
	 * Parses the string from the text document into useful information 
	 * to be used to create a new object.
	 * 
	 * All declared fields in the object is set by using the data received by the text file 
	 * 
	 * @param wireFormat
	 */
	@Override
	public SerializableObject readDJSON(String wireFormat) {
		
		//System.out.println("In Read DJSON\n");
		
		try{
			if(wireFormat.equals("djson")){
			
				SerializableObject obj = null;
				SerializableObject temp = null;
				String line;
		
				Class<?> serializeObject = null;
			
				while((line = reader.readLine()) != null){
					while(!(line = reader.readLine()).equals("<Object>")){
					
						if(line.equals("<\\Object>")){
						
						    	obj = temp;
						    	
							return obj;
						}
						
						if(line.startsWith("Class: ")){
							String className = line.substring(7, line.length());
							
							//System.out.println(className);
							
							serializeObject = Class.forName(className);
							
						 	temp = (SerializableObject)serializeObject.newInstance();
						}
						
						if(line.startsWith("Field Name: ")){
						
							//Object param = null;
							Method methodList[] = serializeObject.getDeclaredMethods();
							line = reader.readLine();
							String fieldType = line.substring(9, line.length());
							
							line = reader.readLine();
							String fieldValue = line.substring(10, line.length());
							
							//System.out.println(fieldType + " " + fieldName + " = " + fieldValue);
							if(fieldType.equals("int")){
								//param = new Integer(Integer.parseInt(fieldValue));
								
								int param = Integer.parseInt(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
									if (methodList[i].getName().startsWith("set")){
									 	if (methodList[i].getName().endsWith("ii")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											
										}
									}
								}
							
							}else if(fieldType.equals("float")){
								//param = new Float(Float.parseFloat(fieldValue));
							
								float param = Float.parseFloat(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
										if (methodList[i].getName().startsWith("set")){
										 	if (methodList[i].getName().endsWith("ff")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											}
										}
									
								}
							
							}else if(fieldType.equals("double")){
								//param = new Double(Double.parseDouble(fieldValue));
								
								double param = Double.parseDouble(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
										if (methodList[i].getName().startsWith("set")){
										 	if (methodList[i].getName().endsWith("dd")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											}
										}
									
								}
							
							}else if(fieldType.equals("long")){
								//param = new Long(Long.parseLong(fieldValue));
								
								long param = Long.parseLong(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
										if (methodList[i].getName().startsWith("set")){
										 	if (methodList[i].getName().endsWith("ll")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											}
										}
									
								}
							
							}else if(fieldType.equals("short")){
								//param = new Short(Short.parseShort(fieldValue));
								
								short param = Short.parseShort(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
										if (methodList[i].getName().startsWith("set")){
										 	if (methodList[i].getName().endsWith("ss")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											}
										}
								}
							
							}else if(fieldType.equals("boolean")){
								//param = new Boolean(Boolean.parseBoolean(fieldValue));
								
								boolean param = Boolean.parseBoolean(fieldValue);
								
								for(int i=0;i<methodList.length;i++){
										if (methodList[i].getName().startsWith("set")){
										 	if (methodList[i].getName().endsWith("bb")){
											// MZ: Method found, run it
												try
												{
													methodList[i].invoke(temp, (Object)param);

												}catch (IllegalAccessException e){
						
													e.printStackTrace();
													System.exit(0);
								
												}catch (InvocationTargetException e){
								
													e.printStackTrace();
													System.exit(0);
								
												}
											}
										}
								}
							
							}
							
							/*
							for(int i=0;i<methodList.length;i++){
								for(int k=0;k<fieldList.length;k++){	 
									if (methodList[i].getName().startsWith("set")){
									 	if (methodList[i].getName().endsWith(fieldList[k].getName().toLowerCase())){
										// MZ: Method found, run it
											try
											{
												methodList[i].invoke(temp, (Object)param);

											}catch (IllegalAccessException e){
						
												e.printStackTrace();
												System.exit(0);
								
											}catch (InvocationTargetException e){
								
												e.printStackTrace();
												System.exit(0);
								
											}
										}
									}
								}
							}
							*/
						}
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.exit(0);
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return null;
		
	}

	
	/**
	 * Gather information about the object passed through the parameters. 
	 * All information about the class name and declared fields are samed into a data structure. 
	 * All information gathered is written on to a text document in a specific format
	 * such that information can be easily parsed and formatted back to useful data.
	 * 
	 * All object information is separated by <Object> <\Object> 
	 * 
	 * @param aRecord 
	 * @param wireFormat
	 */
	@Override
	public void writeDJSON(SerializableObject aRecord, String wireFormat) {

		//System.out.println("In Write DJSON\n");
		//System.out.println("--------------------------------------------------\n");
		writer.println("<Object>");
		
		//try {
			
			if(wireFormat.equals("djson")){
			
				//System.out.println("Class: " + aRecord.getClass().getName());
				writer.println("Class: " + aRecord.getClass().getName() + "\n");
				
						
				Method methodList[] = aRecord.getClass().getDeclaredMethods();
				Field fieldList[] = aRecord.getClass().getDeclaredFields();
				Object result[] = new Object[fieldList.length];
				
				for(int i=0;i<methodList.length;i++){
					
					//System.out.println("Method " + i + ": " + methodList[i].toString());
					//writer.println("Method " + i + ": " + methodList[i].toString());
					
					Class<?> paramList[] = methodList[i].getParameterTypes();
					/*
					for(int j=0;j<paramList.length;j++){
					//	System.out.println("Param: " + paramList[j].toString());
						writer.println("Param: " + paramList[j].toString());
					}	
					
					//System.out.println();
					writer.println();
					*/
								
					for(int k=0;k<fieldList.length;k++){	 
						if (methodList[i].getName().startsWith("get")){
						 	if (methodList[i].getName().endsWith(fieldList[k].getName().toLowerCase())){
							// MZ: Method found, run it
								try
								{
									result[k] = (methodList[i].invoke(aRecord, (Object[])paramList));
									/*
									System.out.print(methodList[i].getName() +" : " + fieldList[k].getName());
									System.out.print(" : " + fieldList[k].getType());
									System.out.print(" : " + result[k].toString());
									System.out.println();
									*/
							
								}catch (IllegalAccessException e){
						
									e.printStackTrace();
									System.exit(0);
								
								}catch (InvocationTargetException e){
								
									e.printStackTrace();
									System.exit(0);
								
								}
							}
						}
				
					}
				
				}
				
				//Field fieldList[] = aRecord.getClass().getDeclaredFields();
				for(int k=0;k<fieldList.length;k++){
					
					/*
					System.out.print("Field Name: " + fieldList[k].getName());
					System.out.print(" of Type: " + fieldList[k].getType());
					System.out.print(" of of Value: " + result.get(k).toString());
					System.out.println();
					*/
					
					writer.println("Field Name: " + fieldList[k].getName());
					writer.println("Of Type: " + fieldList[k].getType());
					writer.println("Of Value: " + result[k].toString());
					writer.println();
				}
			}
			
			//System.out.println("<?>");
			writer.println("<\\Object>");
	}	
}
