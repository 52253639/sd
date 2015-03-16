package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FinanceCostClosePeriodFacadeControllerRemoteHome extends EJBHome
{
    FinanceCostClosePeriodFacadeControllerRemote create() throws CreateException, RemoteException;
}