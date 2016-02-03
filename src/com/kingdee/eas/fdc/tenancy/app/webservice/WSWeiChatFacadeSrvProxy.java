package com.kingdee.eas.fdc.tenancy.app.webservice;

import org.apache.axis.Message;

import org.apache.axis.MessageContext;

import org.apache.axis.message.SOAPEnvelope;

import org.apache.axis.message.SOAPHeaderElement;

import com.kingdee.bos.webservice.WSConfig;

import com.kingdee.bos.webservice.WSInvokeException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectMultiPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.orm.core.ORMEngine;
import com.kingdee.bos.webservice.BeanConvertHelper;
import com.kingdee.bos.webservice.BOSTypeConvertor;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.WSConfig;
import com.kingdee.bos.webservice.MetaDataHelper;
import com.kingdee.bos.webservice.login.EASLoginProxy;
import com.kingdee.bos.webservice.login.WSContext;
import com.kingdee.bos.BOSObjectFactory;

public class WSWeiChatFacadeSrvProxy { 

    public String synCustomer( String str ) throws WSInvokeException {
        try {
            return getController().synCustomer(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String synBroker( String str ) throws WSInvokeException {
        try {
            return getController().synBroker(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.tenancy.IWeiChatFacade getController() {
    	EASLoginProxy proxy = new EASLoginProxy();
        WSContext ctx = proxy.login("webservice", "webservice!@#$", "eas", "SND", "L2", 2);
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.tenancy.IWeiChatFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.tenancy.WeiChatFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.tenancy.IWeiChatFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.tenancy.WeiChatFacade" , com.kingdee.eas.fdc.tenancy.IWeiChatFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.tenancy.IWeiChatFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.tenancy.WeiChatFacade" , com.kingdee.eas.fdc.tenancy.IWeiChatFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}