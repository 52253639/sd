package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchaseAgioEntryControllerLocalHome extends EJBLocalHome
{
    PurchaseAgioEntryControllerLocal create() throws CreateException;
}