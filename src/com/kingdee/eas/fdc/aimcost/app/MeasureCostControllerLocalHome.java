package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MeasureCostControllerLocalHome extends EJBLocalHome
{
    MeasureCostControllerLocal create() throws CreateException;
}