package com.teamvitalis.vitalis.skilltree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;

public class SkillTreeBuilder {

	private Lang name;
	private ItemStack icon;
	private ClassType classType;
	private MagicType magicType;
	private int id;
	private final List<ISkillTreeObject> parents = new ArrayList<>();
	private final List<BaseCast> abilities = new ArrayList<>();

	public void clear() {
		name = null;
		icon = null;
		id = -1;
		parents.clear();
		abilities.clear();
	}

	public SkillTreeObject buildAndClear() {
		SkillTreeObject skill = build();
		clear();
		return skill;
	}

	/**
	 * This method shouldn't be called, instead call
	 * {@link #buildAndClear()}
	 * @return {@link SkillTreeObject} from builder
	 */
	public SkillTreeObject build() {
		SkillTreeBuilder builder = this;
		SkillTreeObject skill = new SkillTreeObject() {
			public SkillTreeObject init() {
				this.name = builder.name;
				this.icon = builder.icon;
				this.classType = builder.classType;
				this.magicType = builder.magicType;
				this.id = builder.id;
				this.parents.clear();
				this.parents.addAll(builder.parents);
				this.abilities.clear();
				this.abilities.addAll(builder.abilities);
				return this;
			}
		}.init();
		return skill;
	}

	public Lang getName() {
		return name;
	}

	public SkillTreeBuilder setName(Lang name) {
		this.name = name;
		return this;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public SkillTreeBuilder setIcon(ItemStack icon) {
		this.icon = icon;
		return this;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	public SkillTreeBuilder setClassType(ClassType classType) {
		this.classType = classType;
		return this;
	}
	
	public MagicType getMagicType() {
		return magicType;
	}
	
	public SkillTreeBuilder setMagicType(MagicType magicType) {
		this.magicType = magicType;
		return this;
	}

	public int getId() {
		return id;
	}

	public SkillTreeBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public List<ISkillTreeObject> getParents() {
		return parents;
	}

	public List<BaseCast> getAbilities() {
		return abilities;
	}

}