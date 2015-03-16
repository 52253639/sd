package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceAdjustEntryControllerRemoteHome extends EJBHome
{
    PriceAdjustEntryControllerRemote create() throws CreateException, RemoteException;
}