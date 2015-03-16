package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceSetSchemeEntryControllerLocalHome extends EJBLocalHome
{
    PriceSetSchemeEntryControllerLocal create() throws CreateException;
}