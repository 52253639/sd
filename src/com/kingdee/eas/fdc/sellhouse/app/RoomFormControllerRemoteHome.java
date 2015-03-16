package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomFormControllerRemoteHome extends EJBHome
{
    RoomFormControllerRemote create() throws CreateException, RemoteException;
}