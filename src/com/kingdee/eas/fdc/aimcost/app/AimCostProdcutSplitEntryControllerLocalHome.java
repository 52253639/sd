package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostProdcutSplitEntryControllerLocalHome extends EJBLocalHome
{
    AimCostProdcutSplitEntryControllerLocal create() throws CreateException;
}