package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MoneyDefineControllerRemoteHome extends EJBHome
{
    MoneyDefineControllerRemote create() throws CreateException, RemoteException;
}