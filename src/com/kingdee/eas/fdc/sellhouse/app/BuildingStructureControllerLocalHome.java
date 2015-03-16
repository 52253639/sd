package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingStructureControllerLocalHome extends EJBLocalHome
{
    BuildingStructureControllerLocal create() throws CreateException;
}