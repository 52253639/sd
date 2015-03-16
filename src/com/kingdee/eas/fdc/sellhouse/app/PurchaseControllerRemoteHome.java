package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseControllerRemoteHome extends EJBHome
{
    PurchaseControllerRemote create() throws CreateException, RemoteException;
}