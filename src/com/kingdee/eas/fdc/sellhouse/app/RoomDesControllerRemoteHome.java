package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomDesControllerRemoteHome extends EJBHome
{
    RoomDesControllerRemote create() throws CreateException, RemoteException;
}