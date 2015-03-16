package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaySplit4VoucherControllerRemoteHome extends EJBHome
{
    PaySplit4VoucherControllerRemote create() throws CreateException, RemoteException;
}