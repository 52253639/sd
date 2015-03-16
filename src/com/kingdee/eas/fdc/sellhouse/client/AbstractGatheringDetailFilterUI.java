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
public abstract class AbstractGatheringDetailFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractGatheringDetailFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowAll;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdhistory;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByDay;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdaccFund;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdcommissionCharge;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdcompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdelseAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdfisrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdfitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdhouseAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdloanAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdpreconcertMoney;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdpreMoney;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdlateFee;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdreplaceFee;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdpurchaseAmount;
    /**
     * output class constructor
     */
    public AbstractGatheringDetailFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractGatheringDetailFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblQuarterFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblQuarterTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.chkIsShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdhistory = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.radioByDay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDCheckBox2 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdaccFund = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdcommissionCharge = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdcompensateAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdelseAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdfisrtAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdfitmentAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdhouseAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdloanAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdpreconcertMoney = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdpreMoney = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdlateFee = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdreplaceFee = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdpurchaseAmount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.plDateType.setName("plDateType");
        this.contYearTo.setName("contYearTo");
        this.contYearFrom.setName("contYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblQuarterFrom.setName("lblQuarterFrom");
        this.lblQuarterTo.setName("lblQuarterTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.chkIsShowAll.setName("chkIsShowAll");
        this.kdhistory.setName("kdhistory");
        this.kDPanel1.setName("kDPanel1");
        this.pkDateFrom.setName("pkDateFrom");
        this.pkDateTo.setName("pkDateTo");
        this.radioByDay.setName("radioByDay");
        this.radioByMonth.setName("radioByMonth");
        this.radioByQuarter.setName("radioByQuarter");
        this.radioByYear.setName("radioByYear");
        this.spiYearTo.setName("spiYearTo");
        this.spiYearFrom.setName("spiYearFrom");
        this.kDCheckBox2.setName("kDCheckBox2");
        this.kDPanel2.setName("kDPanel2");
        this.kdaccFund.setName("kdaccFund");
        this.kdcommissionCharge.setName("kdcommissionCharge");
        this.kdcompensateAmount.setName("kdcompensateAmount");
        this.kdelseAmount.setName("kdelseAmount");
        this.kdfisrtAmount.setName("kdfisrtAmount");
        this.kdfitmentAmount.setName("kdfitmentAmount");
        this.kdhouseAmount.setName("kdhouseAmount");
        this.kdloanAmount.setName("kdloanAmount");
        this.kdpreconcertMoney.setName("kdpreconcertMoney");
        this.kdpreMoney.setName("kdpreMoney");
        this.kdlateFee.setName("kdlateFee");
        this.kdreplaceFee.setName("kdreplaceFee");
        this.kdpurchaseAmount.setName("kdpurchaseAmount");
        // CustomerQueryPanel
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiMonthFrom
        // spiMonthTo
        // lblQuarterFrom		
        this.lblQuarterFrom.setText(resHelper.getString("lblQuarterFrom.text"));
        // lblQuarterTo		
        this.lblQuarterTo.setText(resHelper.getString("lblQuarterTo.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // chkIsShowAll		
        this.chkIsShowAll.setText(resHelper.getString("chkIsShowAll.text"));		
        this.chkIsShowAll.setVisible(false);
        this.chkIsShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdhistory		
        this.kdhistory.setText(resHelper.getString("kdhistory.text"));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // pkDateFrom
        // pkDateTo
        // radioByDay		
        this.radioByDay.setText(resHelper.getString("radioByDay.text"));
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
        // spiYearTo
        // spiYearFrom
        // kDCheckBox2		
        this.kDCheckBox2.setText(resHelper.getString("kDCheckBox2.text"));
        this.kDCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDCheckBox2_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanel2		
        this.kDPanel2.setBorder(null);
        // kdaccFund		
        this.kdaccFund.setText(resHelper.getString("kdaccFund.text"));
        // kdcommissionCharge		
        this.kdcommissionCharge.setText(resHelper.getString("kdcommissionCharge.text"));
        // kdcompensateAmount		
        this.kdcompensateAmount.setText(resHelper.getString("kdcompensateAmount.text"));
        // kdelseAmount		
        this.kdelseAmount.setText(resHelper.getString("kdelseAmount.text"));
        // kdfisrtAmount		
        this.kdfisrtAmount.setText(resHelper.getString("kdfisrtAmount.text"));
        // kdfitmentAmount		
        this.kdfitmentAmount.setText(resHelper.getString("kdfitmentAmount.text"));
        // kdhouseAmount		
        this.kdhouseAmount.setText(resHelper.getString("kdhouseAmount.text"));
        // kdloanAmount		
        this.kdloanAmount.setText(resHelper.getString("kdloanAmount.text"));
        // kdpreconcertMoney		
        this.kdpreconcertMoney.setText(resHelper.getString("kdpreconcertMoney.text"));
        // kdpreMoney		
        this.kdpreMoney.setText(resHelper.getString("kdpreMoney.text"));
        this.kdpreMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdpreMoney_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdlateFee		
        this.kdlateFee.setText(resHelper.getString("kdlateFee.text"));
        // kdreplaceFee		
        this.kdreplaceFee.setText(resHelper.getString("kdreplaceFee.text"));
        // kdpurchaseAmount		
        this.kdpurchaseAmount.setText(resHelper.getString("kdpurchaseAmount.text"));
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
        this.setBounds(new Rectangle(10, 10, 410, 305));
        this.setLayout(null);
        contDateFrom.setBounds(new Rectangle(10, 14, 270, 19));
        this.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(10, 36, 270, 19));
        this.add(contDateTo, null);
        plDateType.setBounds(new Rectangle(9, 63, 390, 55));
        this.add(plDateType, null);
        contYearTo.setBounds(new Rectangle(10, 36, 180, 19));
        this.add(contYearTo, null);
        contYearFrom.setBounds(new Rectangle(10, 14, 180, 19));
        this.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(213, 14, 49, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(213, 36, 49, 19));
        this.add(spiMonthTo, null);
        lblQuarterFrom.setBounds(new Rectangle(274, 14, 29, 19));
        this.add(lblQuarterFrom, null);
        lblQuarterTo.setBounds(new Rectangle(274, 36, 29, 19));
        this.add(lblQuarterTo, null);
        lblMonthFrom.setBounds(new Rectangle(274, 14, 29, 19));
        this.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(274, 36, 29, 19));
        this.add(lblMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(195, 14, 29, 19));
        this.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(195, 36, 29, 19));
        this.add(lblYearTo, null);
        chkIsShowAll.setBounds(new Rectangle(207, 123, 190, 19));
        this.add(chkIsShowAll, null);
        kdhistory.setBounds(new Rectangle(26, 123, 140, 19));
        this.add(kdhistory, null);
        kDPanel1.setBounds(new Rectangle(9, 144, 390, 155));
        this.add(kDPanel1, null);
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //plDateType
        plDateType.setLayout(null);        radioByDay.setBounds(new Rectangle(18, 19, 61, 19));
        plDateType.add(radioByDay, null);
        radioByMonth.setBounds(new Rectangle(95, 19, 61, 19));
        plDateType.add(radioByMonth, null);
        radioByQuarter.setBounds(new Rectangle(168, 19, 61, 19));
        plDateType.add(radioByQuarter, null);
        radioByYear.setBounds(new Rectangle(261, 19, 61, 19));
        plDateType.add(radioByYear, null);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //kDPanel1
        kDPanel1.setLayout(null);        kDCheckBox2.setBounds(new Rectangle(297, 10, 52, 19));
        kDPanel1.add(kDCheckBox2, null);
        kDPanel2.setBounds(new Rectangle(12, 31, 362, 113));
        kDPanel1.add(kDPanel2, null);
        //kDPanel2
        kDPanel2.setLayout(null);        kdaccFund.setBounds(new Rectangle(285, 34, 68, 19));
        kDPanel2.add(kdaccFund, null);
        kdcommissionCharge.setBounds(new Rectangle(109, 59, 77, 19));
        kDPanel2.add(kdcommissionCharge, null);
        kdcompensateAmount.setBounds(new Rectangle(285, 9, 73, 19));
        kDPanel2.add(kdcompensateAmount, null);
        kdelseAmount.setBounds(new Rectangle(6, 84, 66, 19));
        kDPanel2.add(kdelseAmount, null);
        kdfisrtAmount.setBounds(new Rectangle(6, 34, 66, 19));
        kDPanel2.add(kdfisrtAmount, null);
        kdfitmentAmount.setBounds(new Rectangle(6, 59, 70, 19));
        kDPanel2.add(kdfitmentAmount, null);
        kdhouseAmount.setBounds(new Rectangle(109, 34, 65, 19));
        kDPanel2.add(kdhouseAmount, null);
        kdloanAmount.setBounds(new Rectangle(201, 34, 76, 19));
        kDPanel2.add(kdloanAmount, null);
        kdpreconcertMoney.setBounds(new Rectangle(109, 9, 64, 19));
        kDPanel2.add(kdpreconcertMoney, null);
        kdpreMoney.setBounds(new Rectangle(6, 9, 68, 19));
        kDPanel2.add(kdpreMoney, null);
        kdlateFee.setBounds(new Rectangle(201, 59, 66, 19));
        kDPanel2.add(kdlateFee, null);
        kdreplaceFee.setBounds(new Rectangle(285, 59, 85, 19));
        kDPanel2.add(kdreplaceFee, null);
        kdpurchaseAmount.setBounds(new Rectangle(201, 9, 53, 19));
        kDPanel2.add(kdpurchaseAmount, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.GatheringDetailFilterUIHandler";
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
     * output chkIsShowAll_actionPerformed method
     */
    protected void chkIsShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
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
     * output kDCheckBox2_actionPerformed method
     */
    protected void kDCheckBox2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdpreMoney_actionPerformed method
     */
    protected void kdpreMoney_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "GatheringDetailFilterUI");
    }




}