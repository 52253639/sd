package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaySplit4VoucherControllerLocalHome extends EJBLocalHome
{
    PaySplit4VoucherControllerLocal create() throws CreateException;
}