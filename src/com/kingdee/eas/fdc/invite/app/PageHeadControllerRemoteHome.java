package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PageHeadControllerRemoteHome extends EJBHome
{
    PageHeadControllerRemote create() throws CreateException, RemoteException;
}