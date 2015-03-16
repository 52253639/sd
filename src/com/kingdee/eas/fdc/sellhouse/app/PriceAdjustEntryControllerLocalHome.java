package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceAdjustEntryControllerLocalHome extends EJBLocalHome
{
    PriceAdjustEntryControllerLocal create() throws CreateException;
}