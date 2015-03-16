package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingUnitControllerRemoteHome extends EJBHome
{
    BuildingUnitControllerRemote create() throws CreateException, RemoteException;
}