package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostClosePeriodFacadeControllerRemoteHome extends EJBHome
{
    CostClosePeriodFacadeControllerRemote create() throws CreateException, RemoteException;
}