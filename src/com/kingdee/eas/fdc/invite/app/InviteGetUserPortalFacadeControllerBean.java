package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.jdbc.rowset.IRowSet;

import java.io.File;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class InviteGetUserPortalFacadeControllerBean extends AbstractInviteGetUserPortalFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteGetUserPortalFacadeControllerBean");
    protected String _getUserPortal(Context ctx, String clientIP)throws BOSException, InviteProjectException
    {
    	StringBuffer buffer = new StringBuffer();
		buffer.append("http://");
		buffer.append(clientIP);
		
		String filePath = getPortalValue(ctx);
		logger.info("---"+filePath);
		if(filePath == null)
		{
			buffer.append("/easportal");
		}
		else
		{
			buffer.append(filePath);
		}
		logger.info("---"+filePath);
		buffer.append("/fckeditor/index.html");
		logger.info("---"+buffer.toString());
		return buffer.toString() ;
    }
    private String getPortalValue(Context ctx) throws InviteProjectException
    {
		String filePath = getFilePath(ctx);
		String portal = null;
		try
		{
			if(filePath != null)
			{
				logger.info("begin docBuilderFactory");
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				logger.info("begin docBuilderFactory.newDocumentBuilder()");
				DocumentBuilder docBuilder= docBuilderFactory.newDocumentBuilder();
				logger.info("begin docBuilder.parse(new java.io.File(filePath));");
				Document doc = docBuilder.parse(new java.io.File(filePath));   
				logger.info("begin docBuilder.parse(new java.io.File(filePath));");
				NodeList listOfModule = doc.getElementsByTagName("web");
	         
				
				for(int s = 0; s < listOfModule.getLength() ; s++)
				{
					Node firstPersonNode = listOfModule.item(s);
					
					if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
						Element firstPersonElement = (Element)firstPersonNode;
						NodeList firstNameList = firstPersonElement.getElementsByTagName("web-uri");
						Element firstNameElement = (Element)firstNameList.item(0);

						NodeList textFNList = firstNameElement.getChildNodes();
						if(((Node)textFNList.item(0)).getNodeValue().trim().equals("cp_web.war"))
						{
							NodeList lastNameList = firstPersonElement.getElementsByTagName("context-root");
							Element lastNameElement = (Element)lastNameList.item(0);

							NodeList textLNList = lastNameElement.getChildNodes();

							portal = ((Node)textLNList.item(0)).getNodeValue();
							logger.info("portal="+portal);
							break ;
						}

					}
				}
			}
		}
		
		catch(SAXParseException err)
		{
			logger.error(err);
		}
		catch(SAXException e)
		{
			logger.error(e);
		}
		catch (Throwable t)
		{
			logger.error(t);
		}
		logger.info("portal="+portal);
		if(portal==null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select fportal from t_inv_easportalset");
			try {
				IRowSet rowSet = builder.executeQuery();
				if(rowSet!=null&&rowSet.size()>0){
					rowSet.next();
					portal = rowSet.getString("fportal");
				}
			} catch (Exception e) {
				logger.error(e);
			}
			
		}
		return portal;
    }
    private String getFilePath(Context ctx)
	{
		String filePath = System.getProperty("EAS_HOME");
		String fixedFile = "/server/deploy/eas.ear/META-INF/application.xml";
		filePath = filePath + fixedFile;
		
		return filePath ;
	}
}