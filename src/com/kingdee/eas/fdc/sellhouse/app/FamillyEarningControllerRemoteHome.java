package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FamillyEarningControllerRemoteHome extends EJBHome
{
    FamillyEarningControllerRemote create() throws CreateException, RemoteException;
}