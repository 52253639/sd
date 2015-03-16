/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractDocumentPaperPropertiyUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDocumentPaperPropertiyUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox selPageSize;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSelf;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isDown;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner columnCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner topMarge;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDSpinner bottomMarge;
    protected com.kingdee.bos.ctrl.swing.KDSpinner fontSize;
    protected com.kingdee.bos.ctrl.swing.KDSpinner rightMarge;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel11;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel12;
    protected com.kingdee.bos.ctrl.swing.KDTextField headerStr;
    protected com.kingdee.bos.ctrl.swing.KDTextField footerStr;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel13;
    protected com.kingdee.bos.ctrl.swing.KDSpinner leftMarge;
    protected com.kingdee.bos.ctrl.swing.KDSpinner selfWidth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner selfHeight;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    /**
     * output class constructor
     */
    public AbstractDocumentPaperPropertiyUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractDocumentPaperPropertiyUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.selPageSize = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.isSelf = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isDown = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.columnCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.topMarge = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bottomMarge = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.fontSize = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.rightMarge = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel11 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel12 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.headerStr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.footerStr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel13 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.leftMarge = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.selfWidth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.selfHeight = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabel1.setName("kDLabel1");
        this.selPageSize.setName("selPageSize");
        this.isSelf.setName("isSelf");
        this.isDown.setName("isDown");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.columnCount.setName("columnCount");
        this.topMarge.setName("topMarge");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.kDLabel8.setName("kDLabel8");
        this.kDLabel9.setName("kDLabel9");
        this.bottomMarge.setName("bottomMarge");
        this.fontSize.setName("fontSize");
        this.rightMarge.setName("rightMarge");
        this.kDLabel10.setName("kDLabel10");
        this.kDLabel11.setName("kDLabel11");
        this.kDLabel12.setName("kDLabel12");
        this.headerStr.setName("headerStr");
        this.footerStr.setName("footerStr");
        this.kDLabel13.setName("kDLabel13");
        this.leftMarge.setName("leftMarge");
        this.selfWidth.setName("selfWidth");
        this.selfHeight.setName("selfHeight");
        this.btnOK.setName("btnOK");
        this.btnCancel.setName("btnCancel");
        // CoreUI
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // selPageSize		
        this.selPageSize.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.QuestionPaperSizeEnum").toArray());
        // isSelf		
        this.isSelf.setText(resHelper.getString("isSelf.text"));
        this.isSelf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isSelf_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // isDown		
        this.isDown.setText(resHelper.getString("isDown.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // columnCount
        // topMarge
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // bottomMarge
        // fontSize
        // rightMarge
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
        // kDLabel11		
        this.kDLabel11.setText(resHelper.getString("kDLabel11.text"));
        // kDLabel12		
        this.kDLabel12.setText(resHelper.getString("kDLabel12.text"));
        // headerStr		
        this.headerStr.setMaxLength(100);
        // footerStr		
        this.footerStr.setMaxLength(100);
        // kDLabel13		
        this.kDLabel13.setText(resHelper.getString("kDLabel13.text"));
        // leftMarge
        // selfWidth
        // selfHeight
        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 365, 300));
        this.setLayout(null);
        kDLabel1.setBounds(new Rectangle(10, 13, 51, 19));
        this.add(kDLabel1, null);
        selPageSize.setBounds(new Rectangle(25, 32, 92, 19));
        this.add(selPageSize, null);
        isSelf.setBounds(new Rectangle(25, 55, 134, 19));
        this.add(isSelf, null);
        isDown.setBounds(new Rectangle(122, 33, 76, 19));
        this.add(isDown, null);
        kDLabel2.setBounds(new Rectangle(40, 76, 33, 19));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(40, 100, 30, 19));
        this.add(kDLabel3, null);
        columnCount.setBounds(new Rectangle(108, 123, 57, 19));
        this.add(columnCount, null);
        topMarge.setBounds(new Rectangle(286, 36, 51, 19));
        this.add(topMarge, null);
        kDLabel4.setBounds(new Rectangle(10, 123, 100, 19));
        this.add(kDLabel4, null);
        kDLabel5.setBounds(new Rectangle(216, 13, 113, 19));
        this.add(kDLabel5, null);
        kDLabel6.setBounds(new Rectangle(227, 36, 47, 19));
        this.add(kDLabel6, null);
        kDLabel7.setBounds(new Rectangle(227, 65, 47, 19));
        this.add(kDLabel7, null);
        kDLabel8.setBounds(new Rectangle(227, 94, 47, 19));
        this.add(kDLabel8, null);
        kDLabel9.setBounds(new Rectangle(227, 125, 47, 19));
        this.add(kDLabel9, null);
        bottomMarge.setBounds(new Rectangle(286, 64, 51, 19));
        this.add(bottomMarge, null);
        fontSize.setBounds(new Rectangle(152, 169, 51, 19));
        this.add(fontSize, null);
        rightMarge.setBounds(new Rectangle(284, 122, 51, 19));
        this.add(rightMarge, null);
        kDLabel10.setBounds(new Rectangle(10, 169, 51, 19));
        this.add(kDLabel10, null);
        kDLabel11.setBounds(new Rectangle(25, 196, 62, 19));
        this.add(kDLabel11, null);
        kDLabel12.setBounds(new Rectangle(25, 224, 54, 19));
        this.add(kDLabel12, null);
        headerStr.setBounds(new Rectangle(84, 195, 251, 19));
        this.add(headerStr, null);
        footerStr.setBounds(new Rectangle(84, 224, 251, 19));
        this.add(footerStr, null);
        kDLabel13.setBounds(new Rectangle(93, 169, 52, 19));
        this.add(kDLabel13, null);
        leftMarge.setBounds(new Rectangle(285, 92, 51, 19));
        this.add(leftMarge, null);
        selfWidth.setBounds(new Rectangle(78, 76, 57, 19));
        this.add(selfWidth, null);
        selfHeight.setBounds(new Rectangle(78, 99, 57, 19));
        this.add(selfHeight, null);
        btnOK.setBounds(new Rectangle(58, 258, 73, 21));
        this.add(btnOK, null);
        btnCancel.setBounds(new Rectangle(221, 258, 73, 21));
        this.add(btnCancel, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.DocumentPaperPropertiyUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output isSelf_actionPerformed method
     */
    protected void isSelf_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "DocumentPaperPropertiyUI");
    }




}