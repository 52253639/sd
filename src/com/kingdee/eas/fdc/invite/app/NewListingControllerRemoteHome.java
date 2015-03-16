package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingControllerRemoteHome extends EJBHome
{
    NewListingControllerRemote create() throws CreateException, RemoteException;
}