package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostSplitControllerRemoteHome extends EJBHome
{
    CostSplitControllerRemote create() throws CreateException, RemoteException;
}