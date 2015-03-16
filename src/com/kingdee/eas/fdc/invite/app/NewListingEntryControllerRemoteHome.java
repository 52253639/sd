package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingEntryControllerRemoteHome extends EJBHome
{
    NewListingEntryControllerRemote create() throws CreateException, RemoteException;
}