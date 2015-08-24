package genericCheckpointing.server;

import genericCheckpointing.util.SerializableObject;

public interface RestoreI extends StoreRestoreI{

      SerializableObject readDJSON(String wireFormat);

}
