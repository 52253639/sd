package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SubareaControllerRemoteHome extends EJBHome
{
    SubareaControllerRemote create() throws CreateException, RemoteException;
}