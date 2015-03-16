package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseAgioEntryControllerRemoteHome extends EJBHome
{
    PurchaseAgioEntryControllerRemote create() throws CreateException, RemoteException;
}