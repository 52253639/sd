package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomModelControllerRemoteHome extends EJBHome
{
    RoomModelControllerRemote create() throws CreateException, RemoteException;
}