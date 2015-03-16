package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MeasureEntryControllerLocalHome extends EJBLocalHome
{
    MeasureEntryControllerLocal create() throws CreateException;
}