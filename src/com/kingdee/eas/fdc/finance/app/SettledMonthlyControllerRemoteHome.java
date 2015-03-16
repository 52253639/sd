package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettledMonthlyControllerRemoteHome extends EJBHome
{
    SettledMonthlyControllerRemote create() throws CreateException, RemoteException;
}