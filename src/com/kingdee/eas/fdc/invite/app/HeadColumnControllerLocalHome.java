package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HeadColumnControllerLocalHome extends EJBLocalHome
{
    HeadColumnControllerLocal create() throws CreateException;
}