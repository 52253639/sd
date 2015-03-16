package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MeasureEntryControllerRemoteHome extends EJBHome
{
    MeasureEntryControllerRemote create() throws CreateException, RemoteException;
}