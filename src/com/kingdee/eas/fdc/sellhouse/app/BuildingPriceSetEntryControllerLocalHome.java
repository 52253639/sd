package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPriceSetEntryControllerLocalHome extends EJBLocalHome
{
    BuildingPriceSetEntryControllerLocal create() throws CreateException;
}