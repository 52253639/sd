package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderPlanEntryControllerRemoteHome extends EJBHome
{
    SellOrderPlanEntryControllerRemote create() throws CreateException, RemoteException;
}