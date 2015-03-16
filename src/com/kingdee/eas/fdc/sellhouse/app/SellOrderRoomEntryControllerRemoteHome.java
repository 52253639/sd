package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderRoomEntryControllerRemoteHome extends EJBHome
{
    SellOrderRoomEntryControllerRemote create() throws CreateException, RemoteException;
}