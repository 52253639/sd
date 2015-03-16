package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AimCostControllerLocalHome extends EJBLocalHome
{
    AimCostControllerLocal create() throws CreateException;
}