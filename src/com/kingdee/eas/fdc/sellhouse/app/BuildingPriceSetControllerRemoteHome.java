package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BuildingPriceSetControllerRemoteHome extends EJBHome
{
    BuildingPriceSetControllerRemote create() throws CreateException, RemoteException;
}