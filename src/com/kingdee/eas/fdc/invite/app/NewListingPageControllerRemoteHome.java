package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingPageControllerRemoteHome extends EJBHome
{
    NewListingPageControllerRemote create() throws CreateException, RemoteException;
}