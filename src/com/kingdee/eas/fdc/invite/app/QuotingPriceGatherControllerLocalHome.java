package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface QuotingPriceGatherControllerLocalHome extends EJBLocalHome
{
    QuotingPriceGatherControllerLocal create() throws CreateException;
}