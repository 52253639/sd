package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PurchasePayListEntryControllerLocalHome extends EJBLocalHome
{
    PurchasePayListEntryControllerLocal create() throws CreateException;
}