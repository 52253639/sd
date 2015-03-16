package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletColumnControllerRemoteHome extends EJBHome
{
    NewListTempletColumnControllerRemote create() throws CreateException, RemoteException;
}