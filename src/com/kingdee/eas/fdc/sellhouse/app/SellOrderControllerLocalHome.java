package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SellOrderControllerLocalHome extends EJBLocalHome
{
    SellOrderControllerLocal create() throws CreateException;
}