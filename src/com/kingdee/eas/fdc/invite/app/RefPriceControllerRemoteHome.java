package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RefPriceControllerRemoteHome extends EJBHome
{
    RefPriceControllerRemote create() throws CreateException, RemoteException;
}