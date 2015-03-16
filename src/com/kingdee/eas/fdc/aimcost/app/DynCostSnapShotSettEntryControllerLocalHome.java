package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotSettEntryControllerLocalHome extends EJBLocalHome
{
    DynCostSnapShotSettEntryControllerLocal create() throws CreateException;
}