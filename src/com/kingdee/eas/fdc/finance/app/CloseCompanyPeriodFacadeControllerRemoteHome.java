package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CloseCompanyPeriodFacadeControllerRemoteHome extends EJBHome
{
    CloseCompanyPeriodFacadeControllerRemote create() throws CreateException, RemoteException;
}