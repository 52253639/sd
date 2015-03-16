package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TempletTypeControllerRemoteHome extends EJBHome
{
    TempletTypeControllerRemote create() throws CreateException, RemoteException;
}