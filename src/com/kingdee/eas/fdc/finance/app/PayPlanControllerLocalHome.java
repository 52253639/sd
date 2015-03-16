package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayPlanControllerLocalHome extends EJBLocalHome
{
    PayPlanControllerLocal create() throws CreateException;
}