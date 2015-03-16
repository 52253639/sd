package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomModelControllerLocalHome extends EJBLocalHome
{
    RoomModelControllerLocal create() throws CreateException;
}