package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceSetSchemeControllerLocalHome extends EJBLocalHome
{
    PriceSetSchemeControllerLocal create() throws CreateException;
}