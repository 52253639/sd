package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCCustomerControllerRemoteHome extends EJBHome
{
    FDCCustomerControllerRemote create() throws CreateException, RemoteException;
}