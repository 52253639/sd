package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FIProductSettleBillControllerRemoteHome extends EJBHome
{
    FIProductSettleBillControllerRemote create() throws CreateException, RemoteException;
}