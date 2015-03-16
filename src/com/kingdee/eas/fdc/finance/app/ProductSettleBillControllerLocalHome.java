package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProductSettleBillControllerLocalHome extends EJBLocalHome
{
    ProductSettleBillControllerLocal create() throws CreateException;
}