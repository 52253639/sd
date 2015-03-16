package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface IntendingCostEntryControllerRemoteHome extends EJBHome
{
    IntendingCostEntryControllerRemote create() throws CreateException, RemoteException;
}