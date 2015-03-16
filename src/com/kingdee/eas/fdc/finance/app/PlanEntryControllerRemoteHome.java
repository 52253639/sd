package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PlanEntryControllerRemoteHome extends EJBHome
{
    PlanEntryControllerRemote create() throws CreateException, RemoteException;
}