package genericCheckpointing.server;

import genericCheckpointing.util.SerializableObject;

public interface StoreI extends StoreRestoreI{

	void writeDJSON(SerializableObject aRecord, String wireFormat);

}
