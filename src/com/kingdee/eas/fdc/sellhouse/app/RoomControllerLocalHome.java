package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomControllerLocalHome extends EJBLocalHome
{
    RoomControllerLocal create() throws CreateException;
}