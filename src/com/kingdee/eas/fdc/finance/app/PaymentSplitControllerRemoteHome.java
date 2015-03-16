package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentSplitControllerRemoteHome extends EJBHome
{
    PaymentSplitControllerRemote create() throws CreateException, RemoteException;
}