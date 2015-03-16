package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayListEntryControllerRemoteHome extends EJBHome
{
    PayListEntryControllerRemote create() throws CreateException, RemoteException;
}