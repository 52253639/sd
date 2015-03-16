package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomMortagageControllerLocalHome extends EJBLocalHome
{
    RoomMortagageControllerLocal create() throws CreateException;
}