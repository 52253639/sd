package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FinanceCostClosePeriodFacadeControllerLocalHome extends EJBLocalHome
{
    FinanceCostClosePeriodFacadeControllerLocal create() throws CreateException;
}