package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostSplitEntryControllerRemoteHome extends EJBHome
{
    CostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}