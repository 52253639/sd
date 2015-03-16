package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletEntryControllerRemoteHome extends EJBHome
{
    NewListTempletEntryControllerRemote create() throws CreateException, RemoteException;
}