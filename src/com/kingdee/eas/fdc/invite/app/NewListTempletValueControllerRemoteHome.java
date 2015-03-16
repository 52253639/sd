package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletValueControllerRemoteHome extends EJBHome
{
    NewListTempletValueControllerRemote create() throws CreateException, RemoteException;
}