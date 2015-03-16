package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AdjustRecordEntryControllerRemoteHome extends EJBHome
{
    AdjustRecordEntryControllerRemote create() throws CreateException, RemoteException;
}