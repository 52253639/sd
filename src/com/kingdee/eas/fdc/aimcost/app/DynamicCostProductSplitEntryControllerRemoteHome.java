package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynamicCostProductSplitEntryControllerRemoteHome extends EJBHome
{
    DynamicCostProductSplitEntryControllerRemote create() throws CreateException, RemoteException;
}