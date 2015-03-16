package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderControllerRemoteHome extends EJBHome
{
    SellOrderControllerRemote create() throws CreateException, RemoteException;
}