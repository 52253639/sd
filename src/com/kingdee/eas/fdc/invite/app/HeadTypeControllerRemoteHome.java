package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HeadTypeControllerRemoteHome extends EJBHome
{
    HeadTypeControllerRemote create() throws CreateException, RemoteException;
}