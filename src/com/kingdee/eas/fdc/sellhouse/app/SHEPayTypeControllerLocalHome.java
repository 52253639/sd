package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SHEPayTypeControllerLocalHome extends EJBLocalHome
{
    SHEPayTypeControllerLocal create() throws CreateException;
}