package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface IntendingCostEntryControllerLocalHome extends EJBLocalHome
{
    IntendingCostEntryControllerLocal create() throws CreateException;
}