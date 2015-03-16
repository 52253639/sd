package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPriceSetEntryControllerRemoteHome extends EJBHome
{
    BuildingPriceSetEntryControllerRemote create() throws CreateException, RemoteException;
}