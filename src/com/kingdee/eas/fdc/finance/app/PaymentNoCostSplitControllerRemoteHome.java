package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentNoCostSplitControllerRemoteHome extends EJBHome
{
    PaymentNoCostSplitControllerRemote create() throws CreateException, RemoteException;
}