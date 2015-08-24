package genericCheckpointing.util;

import genericCheckpointing.server.StoreRestoreI;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyCreator implements StoreRestoreI
{
	/**
	 * Creates the proxy needed to create and implements a list of interfaces at runtime.
	 * 
	 * @param interfaceArray
	 * @param handler
	 * @return StoreRestoreI
	 */
	public StoreRestoreI createProxy(Class<?>[] interfaceArray, InvocationHandler handler){
		
		StoreRestoreI storeRestoreRef =
            (StoreRestoreI)
            Proxy.newProxyInstance(
                                   getClass().getClassLoader(),
                                   interfaceArray,
                                   handler
                                   );
		
		return storeRestoreRef;
	}
}
