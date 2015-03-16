package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentNoCostSplitEntryControllerRemoteHome extends EJBHome
{
    PaymentNoCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}