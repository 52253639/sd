package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingControllerLocalHome extends EJBLocalHome
{
    BuildingControllerLocal create() throws CreateException;
}