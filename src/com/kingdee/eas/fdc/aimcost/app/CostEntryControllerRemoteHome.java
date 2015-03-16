package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostEntryControllerRemoteHome extends EJBHome
{
    CostEntryControllerRemote create() throws CreateException, RemoteException;
}