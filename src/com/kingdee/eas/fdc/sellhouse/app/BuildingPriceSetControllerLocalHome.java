package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPriceSetControllerLocalHome extends EJBLocalHome
{
    BuildingPriceSetControllerLocal create() throws CreateException;
}