package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HopedUnitPriceControllerRemoteHome extends EJBHome
{
    HopedUnitPriceControllerRemote create() throws CreateException, RemoteException;
}