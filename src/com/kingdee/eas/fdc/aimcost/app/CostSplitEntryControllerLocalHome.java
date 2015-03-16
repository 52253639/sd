package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostSplitEntryControllerLocalHome extends EJBLocalHome
{
    CostSplitEntryControllerLocal create() throws CreateException;
}