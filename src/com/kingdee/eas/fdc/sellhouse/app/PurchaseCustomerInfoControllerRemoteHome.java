package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseCustomerInfoControllerRemoteHome extends EJBHome
{
    PurchaseCustomerInfoControllerRemote create() throws CreateException, RemoteException;
}