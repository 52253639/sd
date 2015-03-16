/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractProductTypeSellStatFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProductTypeSellStatFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByDay;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer pkBeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterTo;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker beginDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbPreArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbActualArea;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPrePurchaseIntoSaleStat;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsIncludeAttach;
    /**
     * output class constructor
     */
    public AbstractProductTypeSellStatFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProductTypeSellStatFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.radioByDay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.pkBeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblQuarterFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblQuarterTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.beginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.rbBuildArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbRoomArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbPreArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbActualArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.chkIsPrePurchaseIntoSaleStat = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsIncludeAttach = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.plDateType.setName("plDateType");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel1.setName("kDPanel1");
        this.radioByDay.setName("radioByDay");
        this.radioByMonth.setName("radioByMonth");
        this.radioByQuarter.setName("radioByQuarter");
        this.radioByYear.setName("radioByYear");
        this.contDateTo.setName("contDateTo");
        this.contYearTo.setName("contYearTo");
        this.lblYearTo.setName("lblYearTo");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblMonthTo.setName("lblMonthTo");
        this.pkBeginDate.setName("pkBeginDate");
        this.contYearFrom.setName("contYearFrom");
        this.lblYearFrom.setName("lblYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.lblQuarterFrom.setName("lblQuarterFrom");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblQuarterTo.setName("lblQuarterTo");
        this.pkDateTo.setName("pkDateTo");
        this.spiYearTo.setName("spiYearTo");
        this.beginDate.setName("beginDate");
        this.spiYearFrom.setName("spiYearFrom");
        this.rbBuildArea.setName("rbBuildArea");
        this.rbRoomArea.setName("rbRoomArea");
        this.rbPreArea.setName("rbPreArea");
        this.rbActualArea.setName("rbActualArea");
        this.chkIsPrePurchaseIntoSaleStat.setName("chkIsPrePurchaseIntoSaleStat");
        this.chkIsIncludeAttach.setName("chkIsIncludeAttach");
        // CustomerQueryPanel
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // radioByDay		
        this.radioByDay.setText(resHelper.getString("radioByDay.text"));		
        this.radioByDay.setSelected(true);
        this.radioByDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByDay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByMonth		
        this.radioByMonth.setText(resHelper.getString("radioByMonth.text"));
        this.radioByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByQuarter		
        this.radioByQuarter.setText(resHelper.getString("radioByQuarter.text"));
        this.radioByQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByQuarter_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByYear		
        this.radioByYear.setText(resHelper.getString("radioByYear.text"));
        this.radioByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radioByDay);
        this.kDButtonGroup1.add(this.radioByMonth);
        this.kDButtonGroup1.add(this.radioByQuarter);
        this.kDButtonGroup1.add(this.radioByYear);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // spiMonthTo
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // pkBeginDate		
        this.pkBeginDate.setBoundLabelText(resHelper.getString("pkBeginDate.boundLabelText"));		
        this.pkBeginDate.setBoundLabelLength(100);		
        this.pkBeginDate.setBoundLabelUnderline(true);
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // spiMonthFrom
        // lblQuarterFrom		
        this.lblQuarterFrom.setText(resHelper.getString("lblQuarterFrom.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblQuarterTo		
        this.lblQuarterTo.setText(resHelper.getString("lblQuarterTo.text"));
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.rbBuildArea);
        this.kDButtonGroup2.add(this.rbRoomArea);
        // pkDateTo
        // spiYearTo
        // beginDate
        // spiYearFrom
        // rbBuildArea		
        this.rbBuildArea.setText(resHelper.getString("rbBuildArea.text"));		
        this.rbBuildArea.setSelected(true);
        this.rbBuildArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    rbBuildArea_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // rbRoomArea		
        this.rbRoomArea.setText(resHelper.getString("rbRoomArea.text"));
        this.rbRoomArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    rbRoomArea_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // rbPreArea		
        this.rbPreArea.setText(resHelper.getString("rbPreArea.text"));		
        this.rbPreArea.setSelected(true);
        // rbActualArea		
        this.rbActualArea.setText(resHelper.getString("rbActualArea.text"));
        // kDButtonGroup3
        this.kDButtonGroup3.add(this.rbPreArea);
        this.kDButtonGroup3.add(this.rbActualArea);
        // chkIsPrePurchaseIntoSaleStat		
        this.chkIsPrePurchaseIntoSaleStat.setText(resHelper.getString("chkIsPrePurchaseIntoSaleStat.text"));
        // chkIsIncludeAttach		
        this.chkIsIncludeAttach.setText(resHelper.getString("chkIsIncludeAttach.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 420, 330));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 420, 330));
        plDateType.setBounds(new Rectangle(10, 10, 400, 137));
        this.add(plDateType, new KDLayout.Constraints(10, 10, 400, 137, 0));
        kDPanel2.setBounds(new Rectangle(10, 155, 400, 94));
        this.add(kDPanel2, new KDLayout.Constraints(10, 155, 400, 94, 0));
        kDPanel1.setBounds(new Rectangle(10, 256, 400, 56));
        this.add(kDPanel1, new KDLayout.Constraints(10, 256, 400, 56, 0));
        //plDateType
        plDateType.setLayout(null);        radioByDay.setBounds(new Rectangle(28, 94, 61, 19));
        plDateType.add(radioByDay, null);
        radioByMonth.setBounds(new Rectangle(118, 94, 61, 19));
        plDateType.add(radioByMonth, null);
        radioByQuarter.setBounds(new Rectangle(206, 94, 61, 19));
        plDateType.add(radioByQuarter, null);
        radioByYear.setBounds(new Rectangle(294, 94, 61, 19));
        plDateType.add(radioByYear, null);
        contDateTo.setBounds(new Rectangle(17, 58, 270, 19));
        plDateType.add(contDateTo, null);
        contYearTo.setBounds(new Rectangle(17, 58, 180, 19));
        plDateType.add(contYearTo, null);
        lblYearTo.setBounds(new Rectangle(205, 59, 29, 19));
        plDateType.add(lblYearTo, null);
        spiMonthTo.setBounds(new Rectangle(222, 58, 49, 19));
        plDateType.add(spiMonthTo, null);
        lblMonthTo.setBounds(new Rectangle(278, 60, 29, 19));
        plDateType.add(lblMonthTo, null);
        pkBeginDate.setBounds(new Rectangle(17, 24, 270, 19));
        plDateType.add(pkBeginDate, null);
        contYearFrom.setBounds(new Rectangle(17, 24, 180, 19));
        plDateType.add(contYearFrom, null);
        lblYearFrom.setBounds(new Rectangle(205, 25, 29, 19));
        plDateType.add(lblYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(222, 24, 49, 19));
        plDateType.add(spiMonthFrom, null);
        lblQuarterFrom.setBounds(new Rectangle(278, 25, 29, 19));
        plDateType.add(lblQuarterFrom, null);
        lblMonthFrom.setBounds(new Rectangle(278, 25, 29, 19));
        plDateType.add(lblMonthFrom, null);
        lblQuarterTo.setBounds(new Rectangle(278, 60, 29, 19));
        plDateType.add(lblQuarterTo, null);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //pkBeginDate
        pkBeginDate.setBoundEditor(beginDate);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //kDPanel2
        kDPanel2.setLayout(null);        rbBuildArea.setBounds(new Rectangle(28, 19, 140, 19));
        kDPanel2.add(rbBuildArea, null);
        rbRoomArea.setBounds(new Rectangle(206, 19, 140, 19));
        kDPanel2.add(rbRoomArea, null);
        rbPreArea.setBounds(new Rectangle(28, 57, 140, 19));
        kDPanel2.add(rbPreArea, null);
        rbActualArea.setBounds(new Rectangle(206, 56, 140, 19));
        kDPanel2.add(rbActualArea, null);
        //kDPanel1
        kDPanel1.setLayout(null);        chkIsPrePurchaseIntoSaleStat.setBounds(new Rectangle(206, 20, 156, 19));
        kDPanel1.add(chkIsPrePurchaseIntoSaleStat, null);
        chkIsIncludeAttach.setBounds(new Rectangle(28, 19, 132, 19));
        kDPanel1.add(chkIsIncludeAttach, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.ProductTypeSellStatFilterUIHandler";
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
     * output radioByDay_actionPerformed method
     */
    protected void radioByDay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByMonth_actionPerformed method
     */
    protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByQuarter_actionPerformed method
     */
    protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByYear_actionPerformed method
     */
    protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output rbBuildArea_actionPerformed method
     */
    protected void rbBuildArea_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output rbRoomArea_actionPerformed method
     */
    protected void rbRoomArea_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ProductTypeSellStatFilterUI");
    }




}