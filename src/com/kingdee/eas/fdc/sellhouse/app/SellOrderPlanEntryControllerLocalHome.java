package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderPlanEntryControllerLocalHome extends EJBLocalHome
{
    SellOrderPlanEntryControllerLocal create() throws CreateException;
}