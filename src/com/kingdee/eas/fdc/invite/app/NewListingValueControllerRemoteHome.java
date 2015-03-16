package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingValueControllerRemoteHome extends EJBHome
{
    NewListingValueControllerRemote create() throws CreateException, RemoteException;
}