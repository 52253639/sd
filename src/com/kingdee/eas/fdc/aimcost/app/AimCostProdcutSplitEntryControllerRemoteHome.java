package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostProdcutSplitEntryControllerRemoteHome extends EJBHome
{
    AimCostProdcutSplitEntryControllerRemote create() throws CreateException, RemoteException;
}