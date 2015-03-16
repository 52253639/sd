package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseChangeControllerRemoteHome extends EJBHome
{
    PurchaseChangeControllerRemote create() throws CreateException, RemoteException;
}