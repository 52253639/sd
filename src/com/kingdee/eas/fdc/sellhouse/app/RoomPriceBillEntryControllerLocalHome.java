package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPriceBillEntryControllerLocalHome extends EJBLocalHome
{
    RoomPriceBillEntryControllerLocal create() throws CreateException;
}