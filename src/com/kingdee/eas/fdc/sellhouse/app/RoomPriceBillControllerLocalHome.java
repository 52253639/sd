package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomPriceBillControllerLocalHome extends EJBLocalHome
{
    RoomPriceBillControllerLocal create() throws CreateException;
}