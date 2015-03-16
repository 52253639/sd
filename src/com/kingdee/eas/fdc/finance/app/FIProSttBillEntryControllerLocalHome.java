package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FIProSttBillEntryControllerLocalHome extends EJBLocalHome
{
    FIProSttBillEntryControllerLocal create() throws CreateException;
}