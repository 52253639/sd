package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AgioRoomEntryControllerLocalHome extends EJBLocalHome
{
    AgioRoomEntryControllerLocal create() throws CreateException;
}