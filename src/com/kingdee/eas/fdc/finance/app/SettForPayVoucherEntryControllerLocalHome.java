package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettForPayVoucherEntryControllerLocalHome extends EJBLocalHome
{
    SettForPayVoucherEntryControllerLocal create() throws CreateException;
}