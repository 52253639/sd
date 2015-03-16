package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceAdjustControllerRemoteHome extends EJBHome
{
    PriceAdjustControllerRemote create() throws CreateException, RemoteException;
}