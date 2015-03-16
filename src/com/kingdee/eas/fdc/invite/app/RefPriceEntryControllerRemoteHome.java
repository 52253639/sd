package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RefPriceEntryControllerRemoteHome extends EJBHome
{
    RefPriceEntryControllerRemote create() throws CreateException, RemoteException;
}