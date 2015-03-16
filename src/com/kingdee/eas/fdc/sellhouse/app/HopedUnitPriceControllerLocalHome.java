package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HopedUnitPriceControllerLocalHome extends EJBLocalHome
{
    HopedUnitPriceControllerLocal create() throws CreateException;
}