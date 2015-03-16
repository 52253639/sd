package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SHEPayTypeControllerRemoteHome extends EJBHome
{
    SHEPayTypeControllerRemote create() throws CreateException, RemoteException;
}