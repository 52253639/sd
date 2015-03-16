package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettForPayVoucherControllerRemoteHome extends EJBHome
{
    SettForPayVoucherControllerRemote create() throws CreateException, RemoteException;
}