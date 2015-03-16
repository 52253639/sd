package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderRoomEntryControllerLocalHome extends EJBLocalHome
{
    SellOrderRoomEntryControllerLocal create() throws CreateException;
}