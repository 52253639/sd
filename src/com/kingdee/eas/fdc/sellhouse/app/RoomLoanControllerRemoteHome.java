package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomLoanControllerRemoteHome extends EJBHome
{
    RoomLoanControllerRemote create() throws CreateException, RemoteException;
}