package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeRoomControllerLocalHome extends EJBLocalHome
{
    ChangeRoomControllerLocal create() throws CreateException;
}