package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SincerityPurchaseControllerLocalHome extends EJBLocalHome
{
    SincerityPurchaseControllerLocal create() throws CreateException;
}