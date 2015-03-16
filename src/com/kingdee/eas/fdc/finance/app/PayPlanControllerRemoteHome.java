package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayPlanControllerRemoteHome extends EJBHome
{
    PayPlanControllerRemote create() throws CreateException, RemoteException;
}