/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.modelWMS.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for WM_Section
 *  @author Adempiere (generated) 
 *  @version Release 3.5.3a - $Id$ */
public class X_WM_Section extends PO implements I_WM_Section, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20081221L;

    /** Standard Constructor */
    public X_WM_Section (Properties ctx, int WM_Section_ID, String trxName)
    {
      super (ctx, WM_Section_ID, trxName);
      /** if (WM_Section_ID == 0)
        {
			setName (null);
			setWM_Area_ID (0);
			setWM_Section_ID (0);
			setWM_Section_Type_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WM_Section (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_WM_Section[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public org.eevolution.model.I_WM_Area getWM_Area() throws RuntimeException 
    {
        Class<?> clazz = MTable.getClass(org.eevolution.model.I_WM_Area.Table_Name);
        org.eevolution.model.I_WM_Area result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (org.eevolution.model.I_WM_Area)constructor.newInstance(new Object[] {getCtx(), new Integer(getWM_Area_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw new RuntimeException( e );
        }
        return result;
    }

	/** Set Warehouse Area.
		@param WM_Area_ID 
		Warehouse Area allow grouping the Warehouse Section
	  */
	public void setWM_Area_ID (int WM_Area_ID)
	{
		if (WM_Area_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WM_Area_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WM_Area_ID, Integer.valueOf(WM_Area_ID));
	}

	/** Get Warehouse Area.
		@return Warehouse Area allow grouping the Warehouse Section
	  */
	public int getWM_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WM_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Warehouse Section.
		@param WM_Section_ID 
		The Warehouse Section is an grouping of Locators with similar features.
	  */
	public void setWM_Section_ID (int WM_Section_ID)
	{
		if (WM_Section_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WM_Section_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WM_Section_ID, Integer.valueOf(WM_Section_ID));
	}

	/** Get Warehouse Section.
		@return The Warehouse Section is an grouping of Locators with similar features.
	  */
	public int getWM_Section_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WM_Section_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_WM_Section_Type getWM_Section_Type() throws RuntimeException 
    {
        Class<?> clazz = MTable.getClass(org.eevolution.model.I_WM_Section_Type.Table_Name);
        org.eevolution.model.I_WM_Section_Type result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (org.eevolution.model.I_WM_Section_Type)constructor.newInstance(new Object[] {getCtx(), new Integer(getWM_Section_Type_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw new RuntimeException( e );
        }
        return result;
    }

	/** Set Warehouse Section Type.
		@param WM_Section_Type_ID Warehouse Section Type	  */
	public void setWM_Section_Type_ID (int WM_Section_Type_ID)
	{
		if (WM_Section_Type_ID < 1) 
			set_Value (COLUMNNAME_WM_Section_Type_ID, null);
		else 
			set_Value (COLUMNNAME_WM_Section_Type_ID, Integer.valueOf(WM_Section_Type_ID));
	}

	/** Get Warehouse Section Type.
		@return Warehouse Section Type	  */
	public int getWM_Section_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WM_Section_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}