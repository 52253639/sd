package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletControllerRemoteHome extends EJBHome
{
    NewListTempletControllerRemote create() throws CreateException, RemoteException;
}