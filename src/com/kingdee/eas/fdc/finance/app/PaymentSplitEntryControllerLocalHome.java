package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentSplitEntryControllerLocalHome extends EJBLocalHome
{
    PaymentSplitEntryControllerLocal create() throws CreateException;
}