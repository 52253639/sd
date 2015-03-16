package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RptRoomFacadeControllerRemoteHome extends EJBHome
{
    RptRoomFacadeControllerRemote create() throws CreateException, RemoteException;
}