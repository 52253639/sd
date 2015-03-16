package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentNoCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    PaymentNoCostSplitEntryControllerLocal create() throws CreateException;
}