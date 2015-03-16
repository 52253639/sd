package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPayEntryControllerLocalHome extends EJBLocalHome
{
    RoomPayEntryControllerLocal create() throws CreateException;
}