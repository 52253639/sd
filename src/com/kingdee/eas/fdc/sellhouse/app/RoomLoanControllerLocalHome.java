package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomLoanControllerLocalHome extends EJBLocalHome
{
    RoomLoanControllerLocal create() throws CreateException;
}