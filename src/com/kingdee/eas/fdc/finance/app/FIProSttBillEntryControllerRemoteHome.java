package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FIProSttBillEntryControllerRemoteHome extends EJBHome
{
    FIProSttBillEntryControllerRemote create() throws CreateException, RemoteException;
}