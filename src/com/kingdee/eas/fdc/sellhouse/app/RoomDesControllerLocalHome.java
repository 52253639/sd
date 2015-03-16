package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomDesControllerLocalHome extends EJBLocalHome
{
    RoomDesControllerLocal create() throws CreateException;
}