package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProductSettleBillControllerRemoteHome extends EJBHome
{
    ProductSettleBillControllerRemote create() throws CreateException, RemoteException;
}