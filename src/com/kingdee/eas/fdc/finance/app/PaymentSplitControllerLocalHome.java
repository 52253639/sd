package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentSplitControllerLocalHome extends EJBLocalHome
{
    PaymentSplitControllerLocal create() throws CreateException;
}