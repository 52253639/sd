package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BizListEntryControllerRemoteHome extends EJBHome
{
    BizListEntryControllerRemote create() throws CreateException, RemoteException;
}