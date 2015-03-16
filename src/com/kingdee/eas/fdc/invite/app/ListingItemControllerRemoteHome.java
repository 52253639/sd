package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ListingItemControllerRemoteHome extends EJBHome
{
    ListingItemControllerRemote create() throws CreateException, RemoteException;
}