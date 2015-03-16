package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynamicCostControllerRemoteHome extends EJBHome
{
    DynamicCostControllerRemote create() throws CreateException, RemoteException;
}