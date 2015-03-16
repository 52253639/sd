package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomSignContractControllerRemoteHome extends EJBHome
{
    RoomSignContractControllerRemote create() throws CreateException, RemoteException;
}