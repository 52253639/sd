package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PriceSetSchemeControllerRemoteHome extends EJBHome
{
    PriceSetSchemeControllerRemote create() throws CreateException, RemoteException;
}