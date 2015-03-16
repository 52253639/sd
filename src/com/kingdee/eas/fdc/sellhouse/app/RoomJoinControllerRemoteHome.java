package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomJoinControllerRemoteHome extends EJBHome
{
    RoomJoinControllerRemote create() throws CreateException, RemoteException;
}