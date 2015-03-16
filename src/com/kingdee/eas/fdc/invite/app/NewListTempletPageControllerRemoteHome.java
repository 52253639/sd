package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletPageControllerRemoteHome extends EJBHome
{
    NewListTempletPageControllerRemote create() throws CreateException, RemoteException;
}