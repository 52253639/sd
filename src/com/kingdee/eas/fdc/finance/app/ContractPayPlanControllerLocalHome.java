package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractPayPlanControllerLocalHome extends EJBLocalHome
{
    ContractPayPlanControllerLocal create() throws CreateException;
}