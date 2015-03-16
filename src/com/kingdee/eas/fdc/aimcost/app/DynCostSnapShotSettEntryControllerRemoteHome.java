package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotSettEntryControllerRemoteHome extends EJBHome
{
    DynCostSnapShotSettEntryControllerRemote create() throws CreateException, RemoteException;
}