package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface QuitRoomControllerLocalHome extends EJBLocalHome
{
    QuitRoomControllerLocal create() throws CreateException;
}