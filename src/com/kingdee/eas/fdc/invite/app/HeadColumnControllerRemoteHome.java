package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HeadColumnControllerRemoteHome extends EJBHome
{
    HeadColumnControllerRemote create() throws CreateException, RemoteException;
}