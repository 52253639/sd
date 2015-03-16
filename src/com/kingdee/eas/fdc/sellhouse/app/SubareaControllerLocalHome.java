package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SubareaControllerLocalHome extends EJBLocalHome
{
    SubareaControllerLocal create() throws CreateException;
}