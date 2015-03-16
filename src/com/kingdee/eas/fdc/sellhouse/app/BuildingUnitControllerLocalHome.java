package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingUnitControllerLocalHome extends EJBLocalHome
{
    BuildingUnitControllerLocal create() throws CreateException;
}