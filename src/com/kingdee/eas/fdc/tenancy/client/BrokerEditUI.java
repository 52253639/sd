/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.tenancy.BrokerFactory;
import com.kingdee.eas.fdc.tenancy.BrokerInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.hr.emp.client.PhotoPanel;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * output class name
 */
public class BrokerEditUI extends AbstractBrokerEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BrokerEditUI.class);
    public BrokerEditUI() throws Exception
    {
        super();
    }
    public SelectorItemCollection getSelectors(){
		
        SelectorItemCollection sic = super.getSelectors();
        sic.add("idCardPictureURL");
        sic.add("orgUnit.*");
        sic.add("CU.*");
        return sic;
    }
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
	protected IObjectValue createNewData() {
		return new BrokerInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return BrokerFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		
		this.toolBar.setVisible(false);
	}
}