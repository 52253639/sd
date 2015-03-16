package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MoneyDefineControllerLocalHome extends EJBLocalHome
{
    MoneyDefineControllerLocal create() throws CreateException;
}