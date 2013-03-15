package com.massivecraft.massivebooks.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import com.massivecraft.massivebooks.Lang;
import com.massivecraft.mcore.MCore;
import com.massivecraft.mcore.store.Entity;
import com.massivecraft.mcore.util.MUtil;
import com.massivecraft.mcore.util.PermUtil;

public class MConf extends Entity<MConf, String>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	public static MConf get()
	{
		return MConfColl.get().get(MCore.INSTANCE);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public MConf load(MConf that)
	{
		this.newPlayerCommands = that.newPlayerCommands;
		this.permToCopyCost = that.permToCopyCost;
		
		this.itemFrameLoadIfSneakTrue = that.itemFrameLoadIfSneakTrue;
		this.itemFrameLoadIfSneakFalse = that.itemFrameLoadIfSneakFalse;
		
		this.itemFrameDisplaynameIfSneakTrue = that.itemFrameDisplaynameIfSneakTrue;
		this.itemFrameDisplaynameIfSneakFalse = that.itemFrameDisplaynameIfSneakFalse;
		
		this.itemFrameRotateIfSneakTrue = that.itemFrameRotateIfSneakTrue;
		this.itemFrameRotateIfSneakFalse = that.itemFrameRotateIfSneakFalse;
		
		return this;
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private List<String> newPlayerCommands = MUtil.list("/book ensureall {p}");
	public List<String> getNewPlayerCommands() { return new ArrayList<String>(this.newPlayerCommands); }
	public void setNewPlayerCommands(List<String> newPlayerCommands) { this.newPlayerCommands = new ArrayList<String>(newPlayerCommands); this.changed(); }
	
	private Map<String, Double> permToCopyCost = MUtil.map(
		"massivebooks.copycost.free", 0D,
		"massivebooks.copycost.0", 0D,
		"massivebooks.copycost.0.01", 0.01D,
		"massivebooks.copycost.0.02", 0.02D,
		"massivebooks.copycost.0.03", 0.03D,
		"massivebooks.copycost.0.1", 0.1D,
		"massivebooks.copycost.0.2", 0.2D,
		"massivebooks.copycost.0.3", 0.3D,
		"massivebooks.copycost.1", 1D,
		"massivebooks.copycost.2", 2D,
		"massivebooks.copycost.3", 3D,
		"massivebooks.copycost.10", 10D,
		"massivebooks.copycost.20", 20D,
		"massivebooks.copycost.30", 30D,
		"massivebooks.copycost.default", 0D
	);
	public Map<String, Double> getPermToCopyCost() { return new LinkedHashMap<String, Double>(this.permToCopyCost); }
	public void setPermToCopyCost(Map<String, Double> permToCopyCost) { this.permToCopyCost = new LinkedHashMap<String, Double>(permToCopyCost); this.changed(); }
	public double getCopyCost(CommandSender sender)
	{
		if (sender == null) return 0;
		Map<String, Double> perm2val = this.getPermToCopyCost();
		if (perm2val == null) return 0;
		if (perm2val.size() == 0) return 0;
		return PermUtil.pickFirstVal(sender, perm2val);
	}
	
	// ItemFrame Load
	
	private boolean itemFrameLoadIfSneakTrue = false;
	public boolean isItemFrameLoadIfSneakTrue() { return this.itemFrameLoadIfSneakTrue; }
	public void itemFrameLoadIfSneakTrue(boolean itemFrameLoadIfSneakTrue) { this.itemFrameLoadIfSneakTrue = itemFrameLoadIfSneakTrue; this.changed(); }
	
	private boolean itemFrameLoadIfSneakFalse = true;
	public boolean isItemFrameLoadIfSneakFalse() { return this.itemFrameLoadIfSneakFalse; }
	public void itemFrameLoadIfSneakFalse(boolean itemFrameLoadIfSneakFalse) { this.itemFrameLoadIfSneakFalse = itemFrameLoadIfSneakFalse; this.changed(); }

	// ItemFrame Displayname
	
	private boolean itemFrameDisplaynameIfSneakTrue = false;
	public boolean isItemFrameDisplaynameIfSneakTrue() { return this.itemFrameDisplaynameIfSneakTrue; }
	public void itemFrameDisplaynameIfSneakTrue(boolean itemFrameDisplaynameIfSneakTrue) { this.itemFrameDisplaynameIfSneakTrue = itemFrameDisplaynameIfSneakTrue; this.changed(); }
	
	private boolean itemFrameDisplaynameIfSneakFalse = true;
	public boolean isItemFrameDisplaynameIfSneakFalse() { return this.itemFrameDisplaynameIfSneakFalse; }
	public void itemFrameDisplaynameIfSneakFalse(boolean itemFrameDisplaynameIfSneakFalse) { this.itemFrameDisplaynameIfSneakFalse = itemFrameDisplaynameIfSneakFalse; this.changed(); }
	
	// ItemFrame Rotate
	
	private boolean itemFrameRotateIfSneakTrue = true;
	public boolean isItemFrameRotateIfSneakTrue() { return this.itemFrameRotateIfSneakTrue; }
	public void itemFrameRotateIfSneakTrue(boolean itemFrameRotateIfSneakTrue) { this.itemFrameRotateIfSneakTrue = itemFrameRotateIfSneakTrue; this.changed(); }
	
	private boolean itemFrameRotateIfSneakFalse = true;
	public boolean isItemFrameRotateIfSneakFalse() { return this.itemFrameRotateIfSneakFalse; }
	public void itemFrameRotateIfSneakFalse(boolean itemFrameRotateIfSneakFalse) { this.itemFrameRotateIfSneakFalse = itemFrameRotateIfSneakFalse; this.changed(); }
	
	// -------------------------------------------- //
	// UTILS
	// -------------------------------------------- //
	
	public void createUpdatePermissionNodes()
	{
		for (Entry<String, Double> entry : this.getPermToCopyCost().entrySet())
		{
			final String name = entry.getKey();
			final Double copyCost = entry.getValue();
			String description = String.format(Lang.PERMISSION_DESCRIPTION_COPYCOST_TEMPLATE, copyCost);
			PermUtil.get(true, true, name, description, PermissionDefault.FALSE);
		}
	}

}