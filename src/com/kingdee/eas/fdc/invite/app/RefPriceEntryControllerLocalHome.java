package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RefPriceEntryControllerLocalHome extends EJBLocalHome
{
    RefPriceEntryControllerLocal create() throws CreateException;
}