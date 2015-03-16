package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AgioRoomEntryControllerRemoteHome extends EJBHome
{
    AgioRoomEntryControllerRemote create() throws CreateException, RemoteException;
}