package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductBillControllerRemoteHome extends EJBHome
{
    DeductBillControllerRemote create() throws CreateException, RemoteException;
}