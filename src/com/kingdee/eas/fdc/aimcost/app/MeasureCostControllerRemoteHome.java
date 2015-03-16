package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MeasureCostControllerRemoteHome extends EJBHome
{
    MeasureCostControllerRemote create() throws CreateException, RemoteException;
}