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
public abstract class AbstractMarketDisplaySettingUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketDisplaySettingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerSynch;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNameRepeat;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPhoneRepeat;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBussnessFlow;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkMarkInvoice;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblToSysCustomer;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox gxcheck;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDComboBox1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDComboBox2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNameRepeat;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhoneRepeat;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup dupGroup;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioSingle;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton kDRadioMulti;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conMulti;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNameAndPhoneRepeat;
    /**
     * output class constructor
     */
    public AbstractMarketDisplaySettingUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketDisplaySettingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCustomerSynch = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboNameRepeat = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboPhoneRepeat = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pnlBussnessFlow = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkMarkInvoice = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tblToSysCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.gxcheck = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDComboBox1 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDComboBox2 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNameRepeat = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhoneRepeat = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dupGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDRadioSingle = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDRadioMulti = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.conMulti = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboNameAndPhoneRepeat = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.pnlMain.setName("pnlMain");
        this.pnlCustomer.setName("pnlCustomer");
        this.contCustomerSynch.setName("contCustomerSynch");
        this.comboNameRepeat.setName("comboNameRepeat");
        this.comboPhoneRepeat.setName("comboPhoneRepeat");
        this.pnlBussnessFlow.setName("pnlBussnessFlow");
        this.chkMarkInvoice.setName("chkMarkInvoice");
        this.tblToSysCustomer.setName("tblToSysCustomer");
        this.gxcheck.setName("gxcheck");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDComboBox1.setName("kDComboBox1");
        this.kDComboBox2.setName("kDComboBox2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel5.setName("kDLabel5");
        this.kDPanel1.setName("kDPanel1");
        this.contNameRepeat.setName("contNameRepeat");
        this.contPhoneRepeat.setName("contPhoneRepeat");
        this.kDRadioSingle.setName("kDRadioSingle");
        this.kDRadioMulti.setName("kDRadioMulti");
        this.conMulti.setName("conMulti");
        this.comboNameAndPhoneRepeat.setName("comboNameAndPhoneRepeat");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,400));
        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pnlMain
        // pnlCustomer
        // contCustomerSynch		
        this.contCustomerSynch.setBoundLabelText(resHelper.getString("contCustomerSynch.boundLabelText"));
        // comboNameRepeat
        this.comboNameRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboNameRepeat_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboPhoneRepeat
        // pnlBussnessFlow
        // chkMarkInvoice		
        this.chkMarkInvoice.setText(resHelper.getString("chkMarkInvoice.text"));
        // tblToSysCustomer		
        this.tblToSysCustomer.setFormatXml(resHelper.getString("tblToSysCustomer.formatXml"));
        this.tblToSysCustomer.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblToSysCustomer_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // gxcheck		
        this.gxcheck.setText(resHelper.getString("gxcheck.text"));
        this.gxcheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    gxcheck_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setForeground(new java.awt.Color(255,0,0));		
        this.kDLabel1.setAlignmentX(0.5f);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDComboBox1
        // kDComboBox2
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));		
        this.kDLabel5.setForeground(new java.awt.Color(255,0,0));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel1.border.title")));
        // contNameRepeat		
        this.contNameRepeat.setBoundLabelText(resHelper.getString("contNameRepeat.boundLabelText"));		
        this.contNameRepeat.setBoundLabelLength(140);		
        this.contNameRepeat.setBoundLabelUnderline(true);
        // contPhoneRepeat		
        this.contPhoneRepeat.setBoundLabelText(resHelper.getString("contPhoneRepeat.boundLabelText"));		
        this.contPhoneRepeat.setBoundLabelLength(140);		
        this.contPhoneRepeat.setBoundLabelUnderline(true);
        // dupGroup
        this.dupGroup.add(this.kDRadioSingle);
        this.dupGroup.add(this.kDRadioMulti);
        // kDRadioSingle
        // kDRadioMulti
        // conMulti		
        this.conMulti.setBoundLabelText(resHelper.getString("conMulti.boundLabelText"));		
        this.conMulti.setBoundLabelLength(140);		
        this.conMulti.setBoundLabelUnderline(true);
        // comboNameAndPhoneRepeat
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 650, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 400));
        btnYes.setBounds(new Rectangle(442, 369, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(442, 369, 73, 21, 0));
        btnNo.setBounds(new Rectangle(548, 368, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(548, 368, 73, 21, 0));
        pnlMain.setBounds(new Rectangle(6, 7, 638, 353));
        this.add(pnlMain, new KDLayout.Constraints(6, 7, 638, 353, 0));
        //pnlMain
        pnlMain.add(pnlCustomer, resHelper.getString("pnlCustomer.constraints"));
        pnlMain.add(pnlBussnessFlow, resHelper.getString("pnlBussnessFlow.constraints"));
        //pnlCustomer
        pnlCustomer.setLayout(null);        contCustomerSynch.setBounds(new Rectangle(14, 163, 81, 19));
        pnlCustomer.add(contCustomerSynch, null);
        tblToSysCustomer.setBounds(new Rectangle(13, 205, 615, 100));
        pnlCustomer.add(tblToSysCustomer, null);
        gxcheck.setBounds(new Rectangle(13, 129, 265, 19));
        pnlCustomer.add(gxcheck, null);
        kDLabel1.setBounds(new Rectangle(288, 125, 318, 25));
        pnlCustomer.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(13, 96, 289, 19));
        pnlCustomer.add(kDLabel2, null);
        kDComboBox1.setBounds(new Rectangle(397, 96, 45, 19));
        pnlCustomer.add(kDComboBox1, null);
        kDComboBox2.setBounds(new Rectangle(554, 96, 45, 19));
        pnlCustomer.add(kDComboBox2, null);
        kDLabel3.setBounds(new Rectangle(335, 97, 62, 19));
        pnlCustomer.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(497, 97, 56, 19));
        pnlCustomer.add(kDLabel4, null);
        kDLabel5.setBounds(new Rectangle(293, 148, 290, 19));
        pnlCustomer.add(kDLabel5, null);
        kDPanel1.setBounds(new Rectangle(8, 4, 615, 88));
        pnlCustomer.add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(null);        contNameRepeat.setBounds(new Rectangle(34, 17, 270, 19));
        kDPanel1.add(contNameRepeat, null);
        contPhoneRepeat.setBounds(new Rectangle(329, 17, 270, 19));
        kDPanel1.add(contPhoneRepeat, null);
        kDRadioSingle.setBounds(new Rectangle(14, 19, 21, 19));
        kDPanel1.add(kDRadioSingle, null);
        kDRadioMulti.setBounds(new Rectangle(14, 53, 20, 19));
        kDPanel1.add(kDRadioMulti, null);
        conMulti.setBounds(new Rectangle(33, 53, 272, 19));
        kDPanel1.add(conMulti, null);
        //contNameRepeat
        contNameRepeat.setBoundEditor(comboNameRepeat);
        //contPhoneRepeat
        contPhoneRepeat.setBoundEditor(comboPhoneRepeat);
        //conMulti
        conMulti.setBoundEditor(comboNameAndPhoneRepeat);
        //pnlBussnessFlow
        pnlBussnessFlow.setLayout(null);        chkMarkInvoice.setBounds(new Rectangle(5, 14, 248, 19));
        pnlBussnessFlow.add(chkMarkInvoice, null);

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
	    return "com.kingdee.eas.fdc.market.app.MarketDisplaySettingUIHandler";
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
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboNameRepeat_actionPerformed method
     */
    protected void comboNameRepeat_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblToSysCustomer_activeCellChanged method
     */
    protected void tblToSysCustomer_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output gxcheck_actionPerformed method
     */
    protected void gxcheck_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketDisplaySettingUI");
    }




}