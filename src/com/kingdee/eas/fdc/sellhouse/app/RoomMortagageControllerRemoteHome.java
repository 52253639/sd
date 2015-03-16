package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomMortagageControllerRemoteHome extends EJBHome
{
    RoomMortagageControllerRemote create() throws CreateException, RemoteException;
}