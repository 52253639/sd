package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettForPayVoucherEntryControllerRemoteHome extends EJBHome
{
    SettForPayVoucherEntryControllerRemote create() throws CreateException, RemoteException;
}