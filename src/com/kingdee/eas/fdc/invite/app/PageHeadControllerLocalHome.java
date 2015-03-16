package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PageHeadControllerLocalHome extends EJBLocalHome
{
    PageHeadControllerLocal create() throws CreateException;
}