package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomModelTypeControllerLocalHome extends EJBLocalHome
{
    RoomModelTypeControllerLocal create() throws CreateException;
}