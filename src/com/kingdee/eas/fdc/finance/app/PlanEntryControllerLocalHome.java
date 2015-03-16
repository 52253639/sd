package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PlanEntryControllerLocalHome extends EJBLocalHome
{
    PlanEntryControllerLocal create() throws CreateException;
}