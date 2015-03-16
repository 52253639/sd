package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostEntryControllerLocalHome extends EJBLocalHome
{
    CostEntryControllerLocal create() throws CreateException;
}