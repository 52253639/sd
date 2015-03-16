package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomJoinControllerLocalHome extends EJBLocalHome
{
    RoomJoinControllerLocal create() throws CreateException;
}