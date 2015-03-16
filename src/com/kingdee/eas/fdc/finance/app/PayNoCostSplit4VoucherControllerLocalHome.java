package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayNoCostSplit4VoucherControllerLocalHome extends EJBLocalHome
{
    PayNoCostSplit4VoucherControllerLocal create() throws CreateException;
}