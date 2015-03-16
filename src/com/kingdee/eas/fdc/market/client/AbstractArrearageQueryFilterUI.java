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
public abstract class AbstractArrearageQueryFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractArrearageQueryFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker purBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker purEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAppEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioShowDetail;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioShowGroup;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup shotType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField textYuQiDayNum;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkYuQiArage;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkUnYuQiArage;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkYuQiPay;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkPayOnTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkPrePur;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkPurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkSign;
    /**
     * output class constructor
     */
    public AbstractArrearageQueryFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractArrearageQueryFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.purBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.purEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpAppBeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpAppEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.radioShowDetail = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioShowGroup = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.shotType = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.textYuQiDayNum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.checkYuQiArage = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkUnYuQiArage = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkYuQiPay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkPayOnTime = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tblMoneyType = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkPrePur = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkSign = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel1.setName("kDPanel1");
        this.contDateFrom.setName("contDateFrom");
        this.purBeginDate.setName("purBeginDate");
        this.contDateTo.setName("contDateTo");
        this.purEndDate.setName("purEndDate");
        this.prmtCustomer.setName("prmtCustomer");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel4.setName("kDPanel4");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.dpAppBeginDate.setName("dpAppBeginDate");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.dpAppEndDate.setName("dpAppEndDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDPanel5.setName("kDPanel5");
        this.radioShowDetail.setName("radioShowDetail");
        this.radioShowGroup.setName("radioShowGroup");
        this.kDPanel3.setName("kDPanel3");
        this.textYuQiDayNum.setName("textYuQiDayNum");
        this.checkYuQiArage.setName("checkYuQiArage");
        this.checkUnYuQiArage.setName("checkUnYuQiArage");
        this.checkYuQiPay.setName("checkYuQiPay");
        this.checkPayOnTime.setName("checkPayOnTime");
        this.tblMoneyType.setName("tblMoneyType");
        this.kDPanel6.setName("kDPanel6");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.chkPrePur.setName("chkPrePur");
        this.chkPurchase.setName("chkPurchase");
        this.chkSign.setName("chkSign");
        // CustomerQueryPanel
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(60);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // purBeginDate
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(60);		
        this.contDateTo.setBoundLabelUnderline(true);
        // purEndDate
        // prmtCustomer		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.prmtCustomer.setDisplayFormat("$name$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setCommitFormat("$number$");
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(60);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // dpAppBeginDate
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(60);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // dpAppEndDate
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(60);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDPanel5		
        this.kDPanel5.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel5.border.title")));
        // radioShowDetail		
        this.radioShowDetail.setText(resHelper.getString("radioShowDetail.text"));		
        this.radioShowDetail.setSelected(true);
        // radioShowGroup		
        this.radioShowGroup.setText(resHelper.getString("radioShowGroup.text"));
        // shotType
        this.shotType.add(this.radioShowDetail);
        this.shotType.add(this.radioShowGroup);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // textYuQiDayNum		
        this.textYuQiDayNum.setSupportedEmpty(true);
        // checkYuQiArage		
        this.checkYuQiArage.setText(resHelper.getString("checkYuQiArage.text"));
        this.checkYuQiArage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkYuQiArage_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkUnYuQiArage		
        this.checkUnYuQiArage.setText(resHelper.getString("checkUnYuQiArage.text"));
        this.checkUnYuQiArage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkUnYuQiArage_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkYuQiPay		
        this.checkYuQiPay.setText(resHelper.getString("checkYuQiPay.text"));
        this.checkYuQiPay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkYuQiPay_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkPayOnTime		
        this.checkPayOnTime.setText(resHelper.getString("checkPayOnTime.text"));
        this.checkPayOnTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkPayOnTime_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblMoneyType		
        this.tblMoneyType.setFormatXml(resHelper.getString("tblMoneyType.formatXml"));
        this.tblMoneyType.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMoneyType_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDPanel6		
        this.kDPanel6.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel6.border.title")));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(60);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // chkPrePur		
        this.chkPrePur.setText(resHelper.getString("chkPrePur.text"));		
        this.chkPrePur.setSelectState(1);
        // chkPurchase		
        this.chkPurchase.setText(resHelper.getString("chkPurchase.text"));		
        this.chkPurchase.setSelectState(1);
        // chkSign		
        this.chkSign.setText(resHelper.getString("chkSign.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 500, 400));
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(1, 1, 499, 47));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(1, 157, 498, 125));
        this.add(kDPanel2, null);
        kDPanel4.setBounds(new Rectangle(1, 282, 498, 50));
        this.add(kDPanel4, null);
        kDLabelContainer1.setBounds(new Rectangle(20, 48, 171, 19));
        this.add(kDLabelContainer1, null);
        kDPanel5.setBounds(new Rectangle(1, 333, 498, 41));
        this.add(kDPanel5, null);
        kDPanel3.setBounds(new Rectangle(1, 73, 499, 44));
        this.add(kDPanel3, null);
        kDPanel6.setBounds(new Rectangle(1, 117, 500, 41));
        this.add(kDPanel6, null);
        kDLabelContainer2.setBounds(new Rectangle(305, 48, 169, 21));
        this.add(kDLabelContainer2, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(1, 1, 499, 47));        contDateFrom.setBounds(new Rectangle(20, 16, 170, 19));
        kDPanel1.add(contDateFrom, new KDLayout.Constraints(20, 16, 170, 19, 0));
        contDateTo.setBounds(new Rectangle(303, 16, 170, 19));
        kDPanel1.add(contDateTo, new KDLayout.Constraints(303, 16, 170, 19, 0));
        //contDateFrom
        contDateFrom.setBoundEditor(purBeginDate);
        //contDateTo
        contDateTo.setBoundEditor(purEndDate);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(1, 157, 498, 125));        tblMoneyType.setBounds(new Rectangle(13, 15, 466, 96));
        kDPanel2.add(tblMoneyType, new KDLayout.Constraints(13, 15, 466, 96, 0));
        //kDPanel4
        kDPanel4.setLayout(null);        kDLabelContainer3.setBounds(new Rectangle(19, 16, 170, 19));
        kDPanel4.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(273, 16, 170, 19));
        kDPanel4.add(kDLabelContainer4, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(dpAppBeginDate);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dpAppEndDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCustomer);
        //kDPanel5
        kDPanel5.setLayout(null);        radioShowDetail.setBounds(new Rectangle(81, 12, 102, 19));
        kDPanel5.add(radioShowDetail, null);
        radioShowGroup.setBounds(new Rectangle(277, 12, 96, 19));
        kDPanel5.add(radioShowGroup, null);
        //kDPanel3
        kDPanel3.setLayout(null);        checkYuQiArage.setBounds(new Rectangle(16, 15, 80, 19));
        kDPanel3.add(checkYuQiArage, null);
        checkUnYuQiArage.setBounds(new Rectangle(134, 15, 93, 19));
        kDPanel3.add(checkUnYuQiArage, null);
        checkYuQiPay.setBounds(new Rectangle(265, 15, 80, 19));
        kDPanel3.add(checkYuQiPay, null);
        checkPayOnTime.setBounds(new Rectangle(384, 15, 82, 19));
        kDPanel3.add(checkPayOnTime, null);
        //kDPanel6
        kDPanel6.setLayout(null);        chkPrePur.setBounds(new Rectangle(54, 13, 69, 19));
        kDPanel6.add(chkPrePur, null);
        chkPurchase.setBounds(new Rectangle(193, 13, 69, 19));
        kDPanel6.add(chkPurchase, null);
        chkSign.setBounds(new Rectangle(333, 13, 69, 19));
        kDPanel6.add(chkSign, null);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(textYuQiDayNum);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.ArrearageQueryFilterUIHandler";
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
     * output checkYuQiArage_itemStateChanged method
     */
    protected void checkYuQiArage_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output checkUnYuQiArage_itemStateChanged method
     */
    protected void checkUnYuQiArage_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output checkYuQiPay_itemStateChanged method
     */
    protected void checkYuQiPay_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output checkPayOnTime_itemStateChanged method
     */
    protected void checkPayOnTime_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblMoneyType_tableClicked method
     */
    protected void tblMoneyType_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "ArrearageQueryFilterUI");
    }




}