package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RptCustomerFacadeControllerRemoteHome extends EJBHome
{
    RptCustomerFacadeControllerRemote create() throws CreateException, RemoteException;
}