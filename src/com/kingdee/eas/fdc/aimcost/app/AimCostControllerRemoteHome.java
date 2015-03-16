package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostControllerRemoteHome extends EJBHome
{
    AimCostControllerRemote create() throws CreateException, RemoteException;
}