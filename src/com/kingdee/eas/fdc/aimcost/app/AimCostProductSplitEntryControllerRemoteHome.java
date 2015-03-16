package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostProductSplitEntryControllerRemoteHome extends EJBHome
{
    AimCostProductSplitEntryControllerRemote create() throws CreateException, RemoteException;
}