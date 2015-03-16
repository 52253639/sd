package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPayEntryControllerRemoteHome extends EJBHome
{
    RoomPayEntryControllerRemote create() throws CreateException, RemoteException;
}