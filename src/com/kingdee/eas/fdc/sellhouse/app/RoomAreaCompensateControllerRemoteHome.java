package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomAreaCompensateControllerRemoteHome extends EJBHome
{
    RoomAreaCompensateControllerRemote create() throws CreateException, RemoteException;
}