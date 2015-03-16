package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductBillEntryControllerLocalHome extends EJBLocalHome
{
    DeductBillEntryControllerLocal create() throws CreateException;
}