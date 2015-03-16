package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPropertyControllerLocalHome extends EJBLocalHome
{
    BuildingPropertyControllerLocal create() throws CreateException;
}