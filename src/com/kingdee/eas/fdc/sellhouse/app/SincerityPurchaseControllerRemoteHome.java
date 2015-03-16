package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SincerityPurchaseControllerRemoteHome extends EJBHome
{
    SincerityPurchaseControllerRemote create() throws CreateException, RemoteException;
}