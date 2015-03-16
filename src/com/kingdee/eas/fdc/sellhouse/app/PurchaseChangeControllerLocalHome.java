package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseChangeControllerLocalHome extends EJBLocalHome
{
    PurchaseChangeControllerLocal create() throws CreateException;
}