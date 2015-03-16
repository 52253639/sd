package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentNoCostSplitControllerLocalHome extends EJBLocalHome
{
    PaymentNoCostSplitControllerLocal create() throws CreateException;
}