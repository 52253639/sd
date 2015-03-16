package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductBillEntryControllerRemoteHome extends EJBHome
{
    DeductBillEntryControllerRemote create() throws CreateException, RemoteException;
}