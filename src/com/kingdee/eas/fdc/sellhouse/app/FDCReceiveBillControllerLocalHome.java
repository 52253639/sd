package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCReceiveBillControllerLocalHome extends EJBLocalHome
{
    FDCReceiveBillControllerLocal create() throws CreateException;
}