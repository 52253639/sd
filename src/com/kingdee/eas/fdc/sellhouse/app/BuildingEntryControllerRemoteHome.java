package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingEntryControllerRemoteHome extends EJBHome
{
    BuildingEntryControllerRemote create() throws CreateException, RemoteException;
}