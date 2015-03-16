package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostProductSplitEntryControllerLocalHome extends EJBLocalHome
{
    AimCostProductSplitEntryControllerLocal create() throws CreateException;
}