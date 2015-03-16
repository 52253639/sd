package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynamicCostControllerLocalHome extends EJBLocalHome
{
    DynamicCostControllerLocal create() throws CreateException;
}