package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotProTypEntryControllerLocalHome extends EJBLocalHome
{
    DynCostSnapShotProTypEntryControllerLocal create() throws CreateException;
}