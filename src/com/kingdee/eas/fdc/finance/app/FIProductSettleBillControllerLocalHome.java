package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FIProductSettleBillControllerLocalHome extends EJBLocalHome
{
    FIProductSettleBillControllerLocal create() throws CreateException;
}