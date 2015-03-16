package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPriceBillControllerRemoteHome extends EJBHome
{
    RoomPriceBillControllerRemote create() throws CreateException, RemoteException;
}