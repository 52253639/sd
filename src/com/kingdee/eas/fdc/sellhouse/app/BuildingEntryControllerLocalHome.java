package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingEntryControllerLocalHome extends EJBLocalHome
{
    BuildingEntryControllerLocal create() throws CreateException;
}