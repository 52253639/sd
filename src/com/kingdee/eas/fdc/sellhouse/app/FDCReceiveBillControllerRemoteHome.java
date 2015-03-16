package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCReceiveBillControllerRemoteHome extends EJBHome
{
    FDCReceiveBillControllerRemote create() throws CreateException, RemoteException;
}