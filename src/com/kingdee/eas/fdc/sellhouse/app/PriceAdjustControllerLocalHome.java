package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceAdjustControllerLocalHome extends EJBLocalHome
{
    PriceAdjustControllerLocal create() throws CreateException;
}