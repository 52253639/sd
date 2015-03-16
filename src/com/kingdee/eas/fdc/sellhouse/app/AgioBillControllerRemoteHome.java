package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AgioBillControllerRemoteHome extends EJBHome
{
    AgioBillControllerRemote create() throws CreateException, RemoteException;
}