package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomDisplaySetControllerRemoteHome extends EJBHome
{
    RoomDisplaySetControllerRemote create() throws CreateException, RemoteException;
}