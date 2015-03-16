package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FamillyEarningControllerLocalHome extends EJBLocalHome
{
    FamillyEarningControllerLocal create() throws CreateException;
}