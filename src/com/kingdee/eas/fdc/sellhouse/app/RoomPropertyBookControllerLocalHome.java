package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPropertyBookControllerLocalHome extends EJBLocalHome
{
    RoomPropertyBookControllerLocal create() throws CreateException;
}