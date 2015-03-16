package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DynCostSnapShotControllerLocalHome extends EJBLocalHome
{
    DynCostSnapShotControllerLocal create() throws CreateException;
}