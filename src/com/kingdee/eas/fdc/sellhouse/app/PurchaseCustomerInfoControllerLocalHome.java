package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseCustomerInfoControllerLocalHome extends EJBLocalHome
{
    PurchaseCustomerInfoControllerLocal create() throws CreateException;
}