package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductBillControllerLocalHome extends EJBLocalHome
{
    DeductBillControllerLocal create() throws CreateException;
}