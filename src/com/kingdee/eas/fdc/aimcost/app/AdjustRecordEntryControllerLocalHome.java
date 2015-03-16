package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AdjustRecordEntryControllerLocalHome extends EJBLocalHome
{
    AdjustRecordEntryControllerLocal create() throws CreateException;
}