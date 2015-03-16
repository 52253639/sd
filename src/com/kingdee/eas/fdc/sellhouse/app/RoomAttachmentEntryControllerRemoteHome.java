package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomAttachmentEntryControllerRemoteHome extends EJBHome
{
    RoomAttachmentEntryControllerRemote create() throws CreateException, RemoteException;
}