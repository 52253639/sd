package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayNoCostSplit4VoucherControllerRemoteHome extends EJBHome
{
    PayNoCostSplit4VoucherControllerRemote create() throws CreateException, RemoteException;
}