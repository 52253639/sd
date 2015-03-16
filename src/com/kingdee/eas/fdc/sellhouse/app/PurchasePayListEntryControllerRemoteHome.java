package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchasePayListEntryControllerRemoteHome extends EJBHome
{
    PurchasePayListEntryControllerRemote create() throws CreateException, RemoteException;
}