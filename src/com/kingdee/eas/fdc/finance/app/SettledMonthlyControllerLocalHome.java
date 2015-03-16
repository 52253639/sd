package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettledMonthlyControllerLocalHome extends EJBLocalHome
{
    SettledMonthlyControllerLocal create() throws CreateException;
}