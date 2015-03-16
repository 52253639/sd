package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPropertyControllerRemoteHome extends EJBHome
{
    BuildingPropertyControllerRemote create() throws CreateException, RemoteException;
}