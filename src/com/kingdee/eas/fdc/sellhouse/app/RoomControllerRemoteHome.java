package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomControllerRemoteHome extends EJBHome
{
    RoomControllerRemote create() throws CreateException, RemoteException;
}