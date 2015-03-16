package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingStructureControllerRemoteHome extends EJBHome
{
    BuildingStructureControllerRemote create() throws CreateException, RemoteException;
}