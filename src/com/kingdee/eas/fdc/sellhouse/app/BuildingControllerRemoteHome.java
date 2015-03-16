package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingControllerRemoteHome extends EJBHome
{
    BuildingControllerRemote create() throws CreateException, RemoteException;
}