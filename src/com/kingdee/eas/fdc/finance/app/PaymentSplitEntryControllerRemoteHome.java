package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentSplitEntryControllerRemoteHome extends EJBHome
{
    PaymentSplitEntryControllerRemote create() throws CreateException, RemoteException;
}