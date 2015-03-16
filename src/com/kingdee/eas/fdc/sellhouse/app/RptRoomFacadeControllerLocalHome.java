package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RptRoomFacadeControllerLocalHome extends EJBLocalHome
{
    RptRoomFacadeControllerLocal create() throws CreateException;
}