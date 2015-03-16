package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynamicCostProductSplitEntryControllerLocalHome extends EJBLocalHome
{
    DynamicCostProductSplitEntryControllerLocal create() throws CreateException;
}