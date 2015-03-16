package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractPayPlanControllerRemoteHome extends EJBHome
{
    ContractPayPlanControllerRemote create() throws CreateException, RemoteException;
}