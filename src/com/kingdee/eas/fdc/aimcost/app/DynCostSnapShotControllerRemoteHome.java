package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotControllerRemoteHome extends EJBHome
{
    DynCostSnapShotControllerRemote create() throws CreateException, RemoteException;
}