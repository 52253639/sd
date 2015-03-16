package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotProTypEntryControllerRemoteHome extends EJBHome
{
    DynCostSnapShotProTypEntryControllerRemote create() throws CreateException, RemoteException;
}