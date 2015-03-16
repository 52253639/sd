package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingColumnControllerRemoteHome extends EJBHome
{
    NewListingColumnControllerRemote create() throws CreateException, RemoteException;
}