package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellProjectControllerRemoteHome extends EJBHome
{
    SellProjectControllerRemote create() throws CreateException, RemoteException;
}