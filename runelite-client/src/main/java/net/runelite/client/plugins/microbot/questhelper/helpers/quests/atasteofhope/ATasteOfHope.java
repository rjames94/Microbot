/*
 * Copyright (c) 2020, Zoinkwiz
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.microbot.questhelper.helpers.quests.atasteofhope;

import net.runelite.client.plugins.microbot.questhelper.bank.banktab.BankSlotIcons;
import net.runelite.client.plugins.microbot.questhelper.collections.ItemCollections;
import net.runelite.client.plugins.microbot.questhelper.panel.PanelDetails;
import net.runelite.client.plugins.microbot.questhelper.questhelpers.BasicQuestHelper;
import net.runelite.client.plugins.microbot.questhelper.questinfo.QuestHelperQuest;
import net.runelite.client.plugins.microbot.questhelper.requirements.Requirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.conditional.Conditions;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirements;
import net.runelite.client.plugins.microbot.questhelper.requirements.player.SkillRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.quest.QuestRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.LogicType;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.Operation;
import net.runelite.client.plugins.microbot.questhelper.requirements.var.VarbitRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.Zone;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.ZoneRequirement;
import net.runelite.client.plugins.microbot.questhelper.rewards.ItemReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.QuestPointReward;
import net.runelite.client.plugins.microbot.questhelper.steps.*;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;

import java.util.*;

public class ATasteOfHope extends BasicQuestHelper
{
	//Items Required
	ItemRequirement coins1000, knife, emerald, chisel, enchantEmeraldRunesOrTablet, rodOfIvandis, pestleAndMortarHighlighted, vialOfWater,
		combatGear, airRune3, airStaff, cosmicRune, enchantTablet, enchantRunes, vial, herb, meatHighlighted, crushedMeat, unfinishedPotion,
		unfinishedBloodPotion, potion, bloodPotion, bloodVial, oldNotes, flaygianNotes, sickleB, chain, emeraldSickleB, enchantedEmeraldSickleB,
		ivandisFlail, rodOfIvandisHighlighted, ivandisFlailEquipped, emeraldHighlighted, vialOfWaterNoTip, food, pickaxe;

	Requirement inMyrequeBase, inTheatreP1, inTheatreP2, inTheatreP3, inTheatreP4, inTheatreP5, inTheatreP6,
		inSerafinaHouse, inNewBase, inRanisFight, wallPressed, hasVialOrVialOfWater;

	DetailedQuestStep talkToGarth, enterBase, talkToSafalaan, climbRubbleAtBank, talkToHarpert,
		climbRubbleAfterHarpert, climbSteamVent, jumpOffRoof, climbSecondVent, climbUpToRoof,
		climbDownFromRoof, lookThroughWindow, returnToBase, talkToSafalaanAfterSpying,
		pressDecoratedWallReturn, pressDecoratedWallAfterSerafina, talkToFlaygian,
		talkToSafalaanAfterFlaygian, goUpToSerafinaHouse, enterSerafinaHouse, talkToSafalaanInSerafinaHouse,
		getOldNotes, talkToSafalaanWithNotes, enterBaseAfterSerafina,
		killAbomination, enterOldManRalBasement, talkToSafalaanInRalBasement, talkToVertidaInRalBasement,
		readFlaygianNotes, getSickle, getChain, useEmeraldOnSickle, enchantSickle, addSickleToRod,
		talkToSafalaanAfterFlail, talkToKael, killRanis, talkToKaelAgain, enterRalForEnd, talkToSafalaanForEnd,
		talkToSafalaanForAbominationFight, talkToSafalaanAfterAbominationFight, enterRalWithFlail, talkToKaelSidebar,
		killRanisSidebar, pressDecoratedWall;

	PuzzleWrapperStep searchForMeat, searchForHerb, searchForVial, searchForPestle, useHerbOnVial, usePestleOnMeat,
		useMeatOnPotion, usePotionOnDoor, talkToSafalaanAfterPotion, useHerbOnBlood, usePestleOnMeatAgain,
		useMeatOnBlood, useBloodOnDoor;

	//Zones
	Zone myrequeBase, theatreP1, theatreP2, theatreP3, theatreP4, theatreP5, theatreP6, serafinaHouse, newBase, ranisFight;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		initializeRequirements();
		setupConditions();
		setupSteps();
		Map<Integer, QuestStep> steps = new HashMap<>();

		steps.put(0, talkToGarth);
		steps.put(5, talkToGarth);
		steps.put(10, talkToGarth);

		ConditionalStep goTalkToSafalaan = new ConditionalStep(this, pressDecoratedWall);
		goTalkToSafalaan.addStep(inMyrequeBase, talkToSafalaan);
		goTalkToSafalaan.addStep(wallPressed, enterBase);
		steps.put(15, goTalkToSafalaan);

		steps.put(20, climbRubbleAtBank);
		steps.put(25, climbRubbleAtBank);
		steps.put(30, talkToHarpert);
		steps.put(35, talkToHarpert);

		ConditionalStep spy = new ConditionalStep(this, climbRubbleAfterHarpert);
		spy.addStep(inTheatreP6, lookThroughWindow);
		spy.addStep(inTheatreP5, climbDownFromRoof);
		spy.addStep(inTheatreP4, climbUpToRoof);
		spy.addStep(inTheatreP3, climbSecondVent);
		spy.addStep(inTheatreP2, jumpOffRoof);
		spy.addStep(inTheatreP1, climbSteamVent);

		steps.put(40, spy);

		ConditionalStep goReturnToSafalaan = new ConditionalStep(this, pressDecoratedWallReturn);
		goReturnToSafalaan.addStep(inMyrequeBase, talkToSafalaanAfterSpying);
		goReturnToSafalaan.addStep(wallPressed, returnToBase);
		steps.put(45, goReturnToSafalaan);
		steps.put(50, goReturnToSafalaan);

		ConditionalStep goTalkToFlaygian = new ConditionalStep(this, pressDecoratedWallReturn);
		goTalkToFlaygian.addStep(inMyrequeBase, talkToFlaygian);
		goTalkToFlaygian.addStep(wallPressed, returnToBase);
		steps.put(55, goTalkToFlaygian);
		steps.put(60, goTalkToFlaygian);

		ConditionalStep goTalkToSaflaanAfterFlaygian = new ConditionalStep(this, pressDecoratedWallReturn);
		goTalkToSaflaanAfterFlaygian.addStep(inMyrequeBase, talkToSafalaanAfterFlaygian);
		goTalkToSaflaanAfterFlaygian.addStep(wallPressed, returnToBase);
		steps.put(65, goTalkToSaflaanAfterFlaygian);
		steps.put(70, goTalkToSaflaanAfterFlaygian);

		ConditionalStep goToSerafinaHouse = new ConditionalStep(this, enterSerafinaHouse);
		goToSerafinaHouse.addStep(inSerafinaHouse, talkToSafalaanInSerafinaHouse);
		goToSerafinaHouse.addStep(inMyrequeBase, goUpToSerafinaHouse);
		steps.put(75, goToSerafinaHouse);

		ConditionalStep tryFirstPotion = new ConditionalStep(this, enterSerafinaHouse);
		tryFirstPotion.addStep(new Conditions(inSerafinaHouse, potion), usePotionOnDoor);
		tryFirstPotion.addStep(new Conditions(potion), enterSerafinaHouse);
		tryFirstPotion.addStep(new Conditions(crushedMeat, unfinishedPotion), useMeatOnPotion);
		tryFirstPotion.addStep(new Conditions(meatHighlighted, pestleAndMortarHighlighted, unfinishedPotion), usePestleOnMeat);
		tryFirstPotion.addStep(new Conditions(herb, meatHighlighted, pestleAndMortarHighlighted, hasVialOrVialOfWater),
			useHerbOnVial);
		tryFirstPotion.addStep(new Conditions(inSerafinaHouse, herb, meatHighlighted, pestleAndMortarHighlighted), searchForVial);
		tryFirstPotion.addStep(new Conditions(inSerafinaHouse, herb, meatHighlighted), searchForPestle);
		tryFirstPotion.addStep(new Conditions(inSerafinaHouse, herb), searchForMeat);
		tryFirstPotion.addStep(inSerafinaHouse, searchForHerb);
		steps.put(80, tryFirstPotion);
		steps.put(81, tryFirstPotion);

		ConditionalStep trySecondPotion = new ConditionalStep(this, enterSerafinaHouse);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse, bloodPotion), useBloodOnDoor);
		trySecondPotion.addStep(new Conditions(bloodPotion), enterSerafinaHouse);
		trySecondPotion.addStep(new Conditions(crushedMeat, unfinishedBloodPotion), useMeatOnBlood);
		trySecondPotion.addStep(new Conditions(meatHighlighted, pestleAndMortarHighlighted, unfinishedBloodPotion), usePestleOnMeat);
		trySecondPotion.addStep(new Conditions(herb, meatHighlighted, pestleAndMortarHighlighted, bloodVial), useHerbOnBlood);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse, herb, meatHighlighted, bloodVial), searchForPestle);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse, herb, bloodVial), searchForMeat);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse, bloodVial), searchForHerb);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse, vial), talkToSafalaanAfterPotion);
		trySecondPotion.addStep(new Conditions(inSerafinaHouse), searchForVial);
		steps.put(82, trySecondPotion);
		steps.put(83, trySecondPotion);
		steps.put(84, trySecondPotion);

		ConditionalStep goGetNotes = new ConditionalStep(this, enterSerafinaHouse);
		goGetNotes.addStep(new Conditions(inSerafinaHouse, oldNotes), talkToSafalaanWithNotes);
		goGetNotes.addStep(inSerafinaHouse, getOldNotes);
		steps.put(85, goGetNotes);
		steps.put(86, goGetNotes);

		ConditionalStep goStartAbominationFight = new ConditionalStep(this, pressDecoratedWallAfterSerafina);
		goStartAbominationFight.addStep(inMyrequeBase, talkToSafalaanForAbominationFight);
		goStartAbominationFight.addStep(wallPressed, enterBaseAfterSerafina);
		steps.put(90, goStartAbominationFight);

		ConditionalStep goKillAbomination = new ConditionalStep(this, pressDecoratedWallAfterSerafina);
		goKillAbomination.addStep(inMyrequeBase, killAbomination);
		goKillAbomination.addStep(wallPressed, enterBaseAfterSerafina);
		steps.put(95, goKillAbomination);
		steps.put(100, goKillAbomination);

		ConditionalStep goTalkToSafalaanAfterAbomination = new ConditionalStep(this, pressDecoratedWallAfterSerafina);
		goTalkToSafalaanAfterAbomination.addStep(inMyrequeBase, talkToSafalaanAfterAbominationFight);
		goTalkToSafalaanAfterAbomination.addStep(wallPressed, enterBaseAfterSerafina);
		steps.put(105, goTalkToSafalaanAfterAbomination);

		ConditionalStep goToNewBase = new ConditionalStep(this, enterOldManRalBasement);
		goToNewBase.addStep(inNewBase, talkToSafalaanInRalBasement);
		steps.put(110, goToNewBase);
		steps.put(115, goToNewBase);

		ConditionalStep goTalkToVertida = new ConditionalStep(this, enterOldManRalBasement);
		goTalkToVertida.addStep(flaygianNotes, readFlaygianNotes);
		goTalkToVertida.addStep(inNewBase, talkToVertidaInRalBasement);
		steps.put(120, goTalkToVertida);

		ConditionalStep makeFlail = new ConditionalStep(this, enterOldManRalBasement);
		makeFlail.addStep(new Conditions(inNewBase, ivandisFlail), talkToSafalaanAfterFlail);
		makeFlail.addStep(new Conditions(ivandisFlail), enterRalWithFlail);
		makeFlail.addStep(new Conditions(enchantedEmeraldSickleB, chain), addSickleToRod);
		makeFlail.addStep(new Conditions(emeraldSickleB, chain), enchantSickle);
		makeFlail.addStep(new Conditions(sickleB, chain), useEmeraldOnSickle);
		makeFlail.addStep(new Conditions(inNewBase, sickleB), getChain);
		makeFlail.addStep(inNewBase, getSickle);
		steps.put(125, makeFlail);

		ConditionalStep goTalkToSafalaanAfterFlail = new ConditionalStep(this, enterRalWithFlail);
		goTalkToSafalaanAfterFlail.addStep(inNewBase, talkToSafalaanAfterFlail);
		steps.put(130, makeFlail);

		steps.put(135, talkToKael);

		ConditionalStep goFightRanis = new ConditionalStep(this, talkToKael);
		goFightRanis.addStep(inRanisFight, killRanis);
		steps.put(140, goFightRanis);

		steps.put(145, talkToKaelAgain);

		ConditionalStep finishQuest = new ConditionalStep(this, enterRalForEnd);
		finishQuest.addStep(inNewBase, talkToSafalaanForEnd);
		steps.put(150, finishQuest);
		steps.put(155, finishQuest);
		steps.put(160, finishQuest);

		return steps;
	}

	@Override
	protected void setupRequirements()
	{
		coins1000 = new ItemRequirement("Coins", ItemCollections.COINS, 1000);
		knife = new ItemRequirement("Knife", ItemID.KNIFE).isNotConsumed();
		emerald = new ItemRequirement("Emerald", ItemID.EMERALD);
		emeraldHighlighted = new ItemRequirement("Emerald", ItemID.EMERALD);
		emeraldHighlighted.setHighlightInInventory(true);
		chisel = new ItemRequirement("Chisel", ItemID.CHISEL).isNotConsumed();
		airRune3 = new ItemRequirement("Air rune", ItemCollections.AIR_RUNE, 3);
		airStaff = new ItemRequirement("Air staff", ItemCollections.AIR_STAFF).isNotConsumed();
		cosmicRune = new ItemRequirement("Cosmic rune", ItemID.COSMICRUNE);
		pickaxe = new ItemRequirement("Any pickaxe", ItemCollections.PICKAXES).isNotConsumed();
		pickaxe.setTooltip("You can get one from one of the miners in the mine");
		enchantRunes = new ItemRequirements("Emerald enchant runes", new ItemRequirements(LogicType.OR, "3 air runes", airRune3, airStaff), cosmicRune);
		enchantTablet = new ItemRequirement("Emerald enchant tablet", ItemID.POH_TABLET_ENCHANTEMERALD);
		enchantEmeraldRunesOrTablet = new ItemRequirements(LogicType.OR, "Runes or tablet for Enchant Emerald", enchantRunes, enchantTablet);
		rodOfIvandis = new ItemRequirement("Rod of Ivandis", ItemCollections.ROD_OF_IVANDIS);
		rodOfIvandis.setTooltip("You can get another from Veliaf Hurtz in Burgh de Rott AFTER talking to Verdita in " +
			"Old Man Ral's basement during the quest");

		rodOfIvandisHighlighted = new ItemRequirement("Rod of Ivandis", ItemCollections.ROD_OF_IVANDIS);
		rodOfIvandisHighlighted.setTooltip("You can get another from Veliaf Hurtz in Burgh de Rott, in the basement under the pub.");
		rodOfIvandisHighlighted.setHighlightInInventory(true);

		pestleAndMortarHighlighted = new ItemRequirement("Pestle and mortar", ItemID.PESTLE_AND_MORTAR);
		pestleAndMortarHighlighted.setHighlightInInventory(true);
		vialOfWaterNoTip = new ItemRequirement("Vial of water", ItemID.VIAL_WATER);

		vialOfWater = new ItemRequirement("Vial of water", ItemID.VIAL_WATER);
		vialOfWater.setHighlightInInventory(true);
		vialOfWater.setTooltip("You can fill the vial upstairs on the broken fountain");
		combatGear = new ItemRequirement("Combat gear", -1, -1).isNotConsumed();
		combatGear.setDisplayItemId(BankSlotIcons.getCombatGear());
		food = new ItemRequirement("Food", ItemCollections.GOOD_EATING_FOOD, -1);
		vial = new ItemRequirement("Vial", ItemID.VIAL_EMPTY);
		herb = new ItemRequirement("Mysterious herb", ItemID.MYQ4_HERB);
		herb.setHighlightInInventory(true);
		meatHighlighted = new ItemRequirement("Mysterious meat", ItemID.MYQ4_MEAT);
		meatHighlighted.setHighlightInInventory(true);
		crushedMeat = new ItemRequirement("Mysterious meat", ItemID.MYQ4_MEAT_CRUSHED);
		crushedMeat.setHighlightInInventory(true);
		unfinishedPotion = new ItemRequirement("Unfinished potion", ItemID.MYQ4_POTION_UNFINISHED_WATER);
		unfinishedPotion.setHighlightInInventory(true);

		unfinishedBloodPotion = new ItemRequirement("Unfinished blood potion", ItemID.MYQ4_POTION_UNFINISHED);
		unfinishedBloodPotion.setHighlightInInventory(true);

		potion = new ItemRequirement("Potion", ItemID.MYQ4_POTION_WATER);
		potion.setHighlightInInventory(true);
		bloodPotion = new ItemRequirement("Blood potion", ItemID.MYQ4_POTION);
		bloodPotion.setHighlightInInventory(true);

		bloodVial = new ItemRequirement("Vial of blood", ItemID.MYQ4_BLOOD_VIAL);
		bloodVial.setHighlightInInventory(true);

		oldNotes = new ItemRequirement("Old notes", ItemID.MYQ4_NOTES);

		flaygianNotes = new ItemRequirement("Flaygian's notes", ItemID.MYQ4_FLAYGIAN_NOTES);
		flaygianNotes.setHighlightInInventory(true);
		sickleB = new ItemRequirement("Silver sickle (b)", ItemID.SILVER_SICKLE_BLESSED);
		sickleB.setHighlightInInventory(true);
		chain = new ItemRequirement("Silver chain", ItemID.MYQ4_CHAIN);
		chain.setHighlightInInventory(true);
		emeraldSickleB = new ItemRequirement("Emerald sickle (b)", ItemID.SILVER_SICKLE_EMERALD);
		emeraldSickleB.setHighlightInInventory(true);
		enchantedEmeraldSickleB = new ItemRequirement("Enchanted emerald sickle (b)", ItemID.SILVER_SICKLE_ENCHANTED);
		enchantedEmeraldSickleB.setHighlightInInventory(true);

		ivandisFlail = new ItemRequirement("Ivandis flail", ItemID.IVANDIS_FLAIL);
		ivandisFlailEquipped = new ItemRequirement("Ivandis flail", ItemID.IVANDIS_FLAIL, 1, true);
	}

	@Override
	protected void setupZones()
	{
		myrequeBase = new Zone(new WorldPoint(3616, 9616, 0), new WorldPoint(3640, 9647, 0));
		theatreP1 = new Zone(new WorldPoint(3638, 3202, 1), new WorldPoint(3646, 3214, 1));
		theatreP2 = new Zone(new WorldPoint(3642, 3214, 2), new WorldPoint(3645, 3224, 2));
		theatreP3 = new Zone(new WorldPoint(3638, 3224, 1), new WorldPoint(3647, 3235, 1));
		theatreP4 = new Zone(new WorldPoint(3641, 3234, 2), new WorldPoint(3661, 3240, 2));
		theatreP5 = new Zone(new WorldPoint(3661, 3237, 3), new WorldPoint(3664, 3237, 3));
		theatreP6 = new Zone(new WorldPoint(3665, 3222, 2), new WorldPoint(3697, 3240, 2));
		serafinaHouse = new Zone(new WorldPoint(3592, 9671, 0), new WorldPoint(3600, 9683, 0));
		newBase = new Zone(new WorldPoint(3588, 9609, 0), new WorldPoint(3606, 9619, 0));
		ranisFight = new Zone(new WorldPoint(2075, 4888, 0), new WorldPoint(2085, 4895, 0));
	}

	public void setupConditions()
	{
		inMyrequeBase = new ZoneRequirement(myrequeBase);
		inTheatreP1 = new ZoneRequirement(theatreP1);
		inTheatreP2 = new ZoneRequirement(theatreP2);
		inTheatreP3 = new ZoneRequirement(theatreP3);
		inTheatreP4 = new ZoneRequirement(theatreP4);
		inTheatreP5 = new ZoneRequirement(theatreP5);
		inTheatreP6 = new ZoneRequirement(theatreP6);
		inSerafinaHouse = new ZoneRequirement(serafinaHouse);
		inNewBase = new ZoneRequirement(newBase);

		hasVialOrVialOfWater = new Conditions(LogicType.OR, vialOfWater, vial);

		inRanisFight = new ZoneRequirement(ranisFight);

		wallPressed = new VarbitRequirement(VarbitID.MYQ3_HIDEOUT_TRAPDOOR, 1, Operation.GREATER_EQUAL);
	}

	public void setupSteps()
	{
		List<WorldPoint> pathToBase = Arrays.asList(
			new WorldPoint(3623, 3324, 0),
			new WorldPoint(3631, 3324, 0),
			new WorldPoint(3631, 3303, 0),
			new WorldPoint(3628, 3300, 0),
			new WorldPoint(3628, 3294, 0),
			new WorldPoint(3633, 3294, 0),
			new WorldPoint(3633, 3288, 0),
			new WorldPoint(3635, 3288, 0),
			new WorldPoint(3635, 3284, 0),
			new WorldPoint(3632, 3284, 0),
			new WorldPoint(3632, 3277, 0),
			new WorldPoint(3634, 3277, 0),
			new WorldPoint(3634, 3267, 0),
			new WorldPoint(3631, 3267, 0),
			new WorldPoint(3631, 3258, 0),
			new WorldPoint(3631, 3258, 1),
			new WorldPoint(3633, 3256, 1),
			new WorldPoint(3639, 3256, 1),
			new WorldPoint(3639, 3256, 0),
			new WorldPoint(3640, 3256, 0),
			new WorldPoint(3640, 3250, 0)
		);

		List<WorldPoint> pathToSerafina = Arrays.asList(
			new WorldPoint(3640, 3250, 0),
			new WorldPoint(3640, 3256, 0),
			new WorldPoint(3639, 3256, 0),
			new WorldPoint(3639, 3256, 1),
			new WorldPoint(3633, 3256, 1),
			new WorldPoint(3631, 3258, 1),
			new WorldPoint(3631, 3258, 0),
			new WorldPoint(3631, 3267, 0),
			new WorldPoint(3634, 3267, 0),
			new WorldPoint(3634, 3277, 0),
			new WorldPoint(3632, 3277, 0),
			new WorldPoint(3632, 3280, 0),
			new WorldPoint(3623, 3280, 0),
			new WorldPoint(3623, 3283, 0),
			new WorldPoint(3596, 3283, 0),
			new WorldPoint(3596, 3277, 0)
		);

		talkToGarth = new NpcStep(this, NpcID.MYQ4_GARTH, new WorldPoint(3668, 3217, 0), "Talk to Garth outside the Theatre of Blood.");
		talkToGarth.addDialogStep("Yes.");

		pressDecoratedWall = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_SYMBOL_MULTI, new WorldPoint(3638, 3251, 0), "Enter the Meiyerditch Myreque base. The easiest way to get there is to have a Vyrewatch take you to the mines, escape by mining 15 rocks, then head south from there.");
		pressDecoratedWall.setLinePoints(pathToBase);
		enterBase = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_TRAPDOOR_MULTILOC, new WorldPoint(3639, 3249, 0), "Enter the Meiyerditch Myreque base. The easiest way to get there is to have a Vyrewatch take you to the mines, escape by mining 15 rocks, then head south from there.");
		enterBase.setLinePoints(pathToBase);
		enterBase.addSubSteps(pressDecoratedWall);

		talkToSafalaan = new NpcStep(this, NpcID.MYREQUE_PT3_SAFALAAN, new WorldPoint(3627, 9644, 0),
			"Talk to Safalaan in the north room.");

		climbRubbleAtBank = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_RUBBLE_01, new WorldPoint(3642, 3207, 0),
			"Return to the Theatre of Blood and attempt to climb rubble in its south west corner.");
		talkToHarpert = new NpcStep(this, NpcID.MYQ4_HARPERT, new WorldPoint(3644, 3211, 0),
			"Talk to Harpert near the rubble.", coins1000);
		talkToHarpert.addDialogStep("Fine, here's the money.");
		climbRubbleAfterHarpert = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_RUBBLE_01, new WorldPoint(3642, 3207, 0),
			"Attempt to climb the rubble again.");
		climbSteamVent = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_VENT, new WorldPoint(3644, 3214, 1),
			"Climb the vent to the north when the steam stops coming out.");
		jumpOffRoof = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_JUMP_DOWN, new WorldPoint(3644, 3225, 2),
			"Jump off the roof to the north.");
		climbSecondVent = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_VENT, new WorldPoint(3641, 3235, 1),
			"Climb the vent to the north when steam stops coming out.");
		climbUpToRoof = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_ROOF_01, new WorldPoint(3660, 3237, 2),
			"Climb the roof to the east.");
		climbDownFromRoof = new ObjectStep(this, ObjectID.MYQ4_OBSTACLE_ROOF_02, new WorldPoint(3665, 3237, 3),
			"Climb down to the east.");
		lookThroughWindow = new ObjectStep(this, ObjectID.MYQ4_WINDOW, new WorldPoint(3687, 3221, 2),
			"Look through the window at the end of the path.");


		pressDecoratedWallReturn = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_SYMBOL_MULTI, new WorldPoint(3638, 3251, 0),
			"Return to the Meiyerditch Myreque base. The easiest way to get there is to have a Vyrewatch take you to the mines," +
				" escape by mining 15 rocks, then head south from there.");
		pressDecoratedWallReturn.setLinePoints(pathToBase);
		returnToBase = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_TRAPDOOR_MULTILOC, new WorldPoint(3639, 3249, 0),
			"Return to the Meiyerditch Myreque base. The easiest way to get there is to have a Vyrewatch take you to the mines, " +
				"escape by mining 15 rocks, then head south from there.");
		returnToBase.setLinePoints(pathToBase);
		returnToBase.addSubSteps(pressDecoratedWallReturn);

		talkToSafalaanAfterSpying = new NpcStep(this, NpcID.MYREQUE_PT3_SAFALAAN, new WorldPoint(3627, 9644, 0),
			"Talk to Safalaan in the north room.");
		talkToSafalaanAfterSpying.addDialogStep("I do.");

		talkToFlaygian = new NpcStep(this, NpcID.MYQ4_FLAYGIAN_VISIBLE, new WorldPoint(3627, 9644, 0), "Talk to Flaygian.");
		talkToFlaygian.addDialogSteps("Anything to report?", "Why?");
		talkToSafalaanAfterFlaygian = new NpcStep(this, NpcID.MYREQUE_PT3_SAFALAAN, new WorldPoint(3627, 9644, 0),
			"Talk to Safalaan again.");

		goUpToSerafinaHouse = new ObjectStep(this, ObjectID.AREA_SANGUINE_MYREQUE_HIDEOUT_LADDER_UP, new WorldPoint(3626, 9617, 0), "Return to the surface.");
		enterSerafinaHouse = new ObjectStep(this, ObjectID.MYQ4_SERAFINA_STAIRS_DOWN, new WorldPoint(3593, 3274, 0), "Enter Serafina's house in west Meiyerditch.");
		enterSerafinaHouse.setLinePoints(pathToSerafina);
		enterSerafinaHouse.addSubSteps(goUpToSerafinaHouse);

		talkToSafalaanInSerafinaHouse = new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3596, 9675, 0), "Talk to Safalaan.");
		// Talked to Safalaan, 2592 2->0

		searchForHerb = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_HERB_BARREL, new WorldPoint(3599, 9678, 0), "Search the barrel in the north east corner."), "Work out how to open the door in the room.");
		searchForMeat = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_MEAT_CRATE, new WorldPoint(3593, 9677, 0), "Search the crate in the west of the room for mysterious meat."),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		searchForVial = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_VIAL_CRATE, new WorldPoint(3598, 9671, 0), "Search a crate in the south east corner for a vial."),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		searchForPestle = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_PESTLE_CRATE, new WorldPoint(3594, 9671, 0), "Search the crate near the staircase for a pestle and mortar."),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);

		useHerbOnVial = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Add the herb to a vial of water.", herb, vialOfWater),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		usePestleOnMeat = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Use the pestle and mortar on the meat.", pestleAndMortarHighlighted, meatHighlighted),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		useMeatOnPotion = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Add the crushed meat to the potion.", crushedMeat, unfinishedPotion),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		usePotionOnDoor = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_SERAFINA_DOOR, new WorldPoint(3596, 9680, 0), "Use the potion on the door.", potion),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		usePotionOnDoor.addDialogStep("Yes.");
		usePotionOnDoor.addIcon(ItemID.MYQ4_POTION_WATER);
		talkToSafalaanAfterPotion = new PuzzleWrapperStep(this,
			new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3596, 9675, 0), "Talk to Safalaan for his blood.", vial),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		talkToSafalaanAfterPotion.addDialogStep("Yes.");

		useHerbOnBlood = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Add the herb to a vial of blood.", herb, bloodVial),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		usePestleOnMeatAgain = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Use the pestle and mortar on the meat.", pestleAndMortarHighlighted, meatHighlighted),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		useMeatOnBlood = new PuzzleWrapperStep(this,
			new DetailedQuestStep(this, "Add the crushed meat to the unfinished potion.", crushedMeat, unfinishedBloodPotion),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		useBloodOnDoor = new PuzzleWrapperStep(this,
			new ObjectStep(this, ObjectID.MYQ4_SERAFINA_DOOR, new WorldPoint(3596, 9680, 0), "Use the blood potion on the door.", bloodPotion),
			"Work out how to open the door in the room.").withNoHelpHiddenInSidebar(true);
		useBloodOnDoor.addDialogStep("Yes.");
		useBloodOnDoor.addIcon(ItemID.MYQ4_POTION);
		getOldNotes = new ObjectStep(this, ObjectID.MYQ4_CHEST_CLOSED, new WorldPoint(3596, 9683, 0), "Search the chest for some notes.");
		((ObjectStep) (getOldNotes)).addAlternateObjects(ObjectID.MYQ4_CHEST);
		talkToSafalaanWithNotes = new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3596, 9675, 0), "Talk to Safalaan with the notes.", oldNotes);
		talkToSafalaanWithNotes.addDialogStep("Here you go.");

		pressDecoratedWallAfterSerafina = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_SYMBOL_MULTI, new WorldPoint(3638, 3251, 0)
			, "Prepare for a fight, then return to the Meiyerditch Myreque base.");
		pressDecoratedWallAfterSerafina.setLinePoints(pathToBase);

		enterBaseAfterSerafina = new ObjectStep(this, ObjectID.SANG_MYREQUE_HIDEOUT_TRAPDOOR_MULTILOC, new WorldPoint(3639, 3249, 0), "Prepare for a fight, then return to the Meiyerditch Myreque base.");
		enterBaseAfterSerafina.setLinePoints(pathToBase);
		enterBaseAfterSerafina.addSubSteps(pressDecoratedWallAfterSerafina);

		talkToSafalaanForAbominationFight = new NpcStep(this, NpcID.MYREQUE_PT3_SAFALAAN, new WorldPoint(3627, 9644, 0), "Talk to Safalaan, ready for a fight.");
		killAbomination = new NpcStep(this, NpcID.MYQ4_ABOMINATION_CUTSCENE, new WorldPoint(3627, 9644, 0), "Kill the abomination. It can be safe spotted with a long-ranged weapon.");
		((NpcStep) (killAbomination)).addAlternateNpcs(NpcID.MYQ4_ABOMINATION_CUTSCENE_WEAKENED, NpcID.MYQ4_ABOMINATION);
		talkToSafalaanAfterAbominationFight = new NpcStep(this, NpcID.MYQ4_SAFALAAN_HURT_OP, new WorldPoint(3627, 9644, 0), "Talk to Safalaan.");

		enterOldManRalBasement = new ObjectStep(this, ObjectID.MYQ4_HIDEOUT_TRAPDOOR_OPEN, new WorldPoint(3605, 3215, 0), "Climb down the trapdoor in Old Man Ral's house in south west Meiyerditch.",
			rodOfIvandis, emerald, chisel, enchantEmeraldRunesOrTablet);
		((ObjectStep) (enterOldManRalBasement)).addAlternateObjects(ObjectID.MYQ4_HIDEOUT_TRAPDOOR);

		enterRalWithFlail = new ObjectStep(this, ObjectID.MYQ4_HIDEOUT_TRAPDOOR_OPEN, new WorldPoint(3605, 3215, 0), "Climb down the trapdoor in Old Man Ral's house in south west Meiyerditch.",
			ivandisFlail);
		((ObjectStep) (enterRalWithFlail)).addAlternateObjects(ObjectID.MYQ4_HIDEOUT_TRAPDOOR);
		talkToSafalaanInRalBasement = new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3598, 9614, 0), "Talk to Safalaan.");
		talkToVertidaInRalBasement = new NpcStep(this, NpcID.MYQ4_VERTIDA_VISIBLE, new WorldPoint(3598, 9614, 0), "Talk to Vertida.");
		readFlaygianNotes = new DetailedQuestStep(this, "Read Flaygian's notes.", flaygianNotes);
		getSickle = new ObjectStep(this, ObjectID.MYQ4_SICKLE_CRATE, new WorldPoint(3597, 9615, 0), "Search the north west crate for a blessed sickle.");
		getChain = new ObjectStep(this, ObjectID.MYQ4_CHAIN_CRATE, new WorldPoint(3601, 9610, 0), "Search the south east crate for a chain.");
		useEmeraldOnSickle = new DetailedQuestStep(this, "Use an emerald on the blessed sickle.", emeraldHighlighted, sickleB, chisel);
		useEmeraldOnSickle.addDialogStep("Yes.");
		enchantSickle = new DetailedQuestStep(this, "Cast enchant emerald on the emerald sickle.", emeraldSickleB, enchantEmeraldRunesOrTablet);
		addSickleToRod = new DetailedQuestStep(this, "Add the enchanted emerald sickle to the rod of ivandis.", enchantedEmeraldSickleB, rodOfIvandisHighlighted);
		addSickleToRod.addDialogStep("Yes.");
		talkToSafalaanAfterFlail = new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3598, 9614, 0), "Talk to Safalaan again.");
		talkToSafalaanAfterFlail.addSubSteps(enterRalWithFlail);
		talkToKael = new NpcStep(this, NpcID.MYQ4_KAEL_VISIBLE_NOWEAPON, new WorldPoint(3659, 3218, 0), "Talk to Kael outside the Theatre of Blood, prepared to fight Ranis.", ivandisFlailEquipped);
		talkToKael.addText("He can only be hurt by the flail, and uses magic and melee attacks.");
		talkToKael.addText("He will occasionally charge an attack and explode, dealing damage close to him. Just run away for this attack.");
		talkToKael.addText("During the fight he will spawn vyrewatch which you'll need to kill. Whilst fighting them Ranis will be throwing blood bombs at your current location, so make sure to move around.");
		talkToKael.addText("In his last phase he will only attack with melee, so make sure to use protect from melee!");
		talkToKael.addDialogStep("I'm ready.");

		talkToKaelSidebar = new NpcStep(this, NpcID.MYQ4_KAEL_VISIBLE_NOWEAPON, new WorldPoint(3659, 3218, 0), "Talk to Kael outside the Theatre of Blood, prepared to fight Ranis.", ivandisFlailEquipped);
		talkToKaelSidebar.addSubSteps(talkToKael);

		killRanisSidebar = new NpcStep(this, NpcID.MYQ4_RANIS_VAMPYRE_COMBAT, new WorldPoint(2082, 4891, 0), "Defeat Ranis.", ivandisFlailEquipped);
		((NpcStep) (killRanisSidebar)).addAlternateNpcs(NpcID.MYQ4_RANIS_VAMPYRE_TRANSITION, NpcID.MYQ4_RANIS_VAMPYRE_FLYING_COMBAT, NpcID.MYQ4_RANIS_VAMPYRE_FLYING_TRANSITION, NpcID.MYQ4_RANIS_VAMPYRE_COMBAT_ENRAGE);
		killRanisSidebar.addText("He can only be hurt by the flail, and uses magic and melee attacks.");
		killRanisSidebar.addText("He will occasionally charge an attack and explode, dealing damage close to him. Just run away for this attack.");
		killRanisSidebar.addText("During the fight he will spawn vyrewatch which you'll need to kill. Whilst fighting them Ranis will be throwing blood bombs at your current location, so make sure to move around.");
		killRanisSidebar.addText("In his last phase he will only attack with melee, so make sure to use protect from melee!");


		killRanis = new NpcStep(this, NpcID.MYQ4_RANIS_VAMPYRE_COMBAT, new WorldPoint(2082, 4891, 0), "Defeat Ranis. His " +
			"various mechanics are listed in the helper's sidebar.", ivandisFlailEquipped, food);
		((NpcStep) (killRanis)).addAlternateNpcs(NpcID.MYQ4_RANIS_VAMPYRE_TRANSITION, NpcID.MYQ4_RANIS_VAMPYRE_FLYING_COMBAT, NpcID.MYQ4_RANIS_VAMPYRE_FLYING_TRANSITION, NpcID.MYQ4_RANIS_VAMPYRE_COMBAT_ENRAGE);
		killRanisSidebar.addSubSteps(killRanis);

		talkToKaelAgain = new NpcStep(this, NpcID.MYQ4_KAEL_VISIBLE_NOWEAPON, new WorldPoint(3659, 3218, 0), "Talk to Kael outside the Theatre of Blood again.");
		enterRalForEnd = new ObjectStep(this, ObjectID.MYQ4_HIDEOUT_TRAPDOOR_OPEN, new WorldPoint(3605, 3215, 0), "Climb down the trapdoor in Old Man Ral's house in south west Meiyerditch.");
		((ObjectStep) (enterRalForEnd)).addAlternateObjects(ObjectID.MYQ4_HIDEOUT_TRAPDOOR);
		talkToSafalaanForEnd = new NpcStep(this, NpcID.MYQ4_SAFALAAN_VISIBLE, new WorldPoint(3598, 9614, 0), "Talk to Safalaan to complete the quest!");
	}

	@Override
	public List<ItemRequirement> getItemRequirements()
	{
		return Arrays.asList(coins1000, vialOfWaterNoTip, rodOfIvandis, emerald, chisel,
			enchantEmeraldRunesOrTablet, combatGear);
	}

	@Override
	public List<ItemRequirement> getItemRecommended()
	{
		return Collections.singletonList(pickaxe);
	}

	@Override
	public List<String> getCombatRequirements()
	{
		return Arrays.asList("Abomination (level 149, safespottable)", "Ranis Drakan (level 233, melee only)");
	}

	@Override
	public QuestPointReward getQuestPointReward()
	{
		return new QuestPointReward(1);
	}

	@Override
	public List<ItemReward> getItemRewards()
	{
		return Arrays.asList(
				new ItemReward("Ivandis Flail", ItemID.IVANDIS_FLAIL, 1),
				new ItemReward("Drakan's Medallion", ItemID.DRAKANS_MEDALLION, 1),
				new ItemReward("2,500 Experience Tomes (Any skill over level 35).", ItemID.MYQ4_XP_TOME, 3) //22415 is placeholder
		);
	}

	@Override
	public List<PanelDetails> getPanels()
	{
		List<PanelDetails> allSteps = new ArrayList<>();
		allSteps.add(new PanelDetails("Starting off", Arrays.asList(talkToGarth, enterBase,
			talkToSafalaan)));
		allSteps.add(new PanelDetails("Spying",
			Arrays.asList(climbRubbleAtBank, talkToHarpert, climbRubbleAfterHarpert,
				climbSteamVent, jumpOffRoof, climbSecondVent, climbUpToRoof, climbDownFromRoof,
				lookThroughWindow), coins1000));
		allSteps.add(new PanelDetails("Investigating",
			Arrays.asList(returnToBase, talkToSafalaanAfterSpying, talkToFlaygian, talkToSafalaanAfterFlaygian, enterSerafinaHouse,
				talkToSafalaanInSerafinaHouse, searchForHerb, searchForMeat, searchForPestle, useHerbOnVial, usePestleOnMeat,
				useMeatOnPotion, usePotionOnDoor, talkToSafalaanAfterPotion, useHerbOnBlood, usePestleOnMeatAgain, useMeatOnBlood,
				useBloodOnDoor, getOldNotes, talkToSafalaanWithNotes, enterBaseAfterSerafina, talkToSafalaanForAbominationFight, killAbomination,
				talkToSafalaanAfterAbominationFight), combatGear, food, vialOfWaterNoTip));
		allSteps.add(new PanelDetails("Plotting revenge",
			Arrays.asList(enterOldManRalBasement, talkToSafalaanInRalBasement,
				talkToVertidaInRalBasement, readFlaygianNotes, getSickle, getChain, useEmeraldOnSickle,
				enchantSickle, addSickleToRod, talkToSafalaanAfterFlail), emerald, chisel, enchantEmeraldRunesOrTablet));
		allSteps.add(new PanelDetails("Rising up",
			Arrays.asList(talkToKaelSidebar, killRanisSidebar, talkToKaelAgain, enterRalForEnd, talkToSafalaanForEnd),
			combatGear, food, ivandisFlail));
		return allSteps;
	}

	@Override
	public List<Requirement> getGeneralRequirements()
	{
		ArrayList<Requirement> req = new ArrayList<>();
		req.add(new QuestRequirement(QuestHelperQuest.DARKNESS_OF_HALLOWVALE, QuestState.FINISHED));
		req.add(new SkillRequirement(Skill.CRAFTING, 48));
		req.add(new SkillRequirement(Skill.AGILITY, 45));
		req.add(new SkillRequirement(Skill.ATTACK, 40));
		req.add(new SkillRequirement(Skill.HERBLORE, 40));
		req.add(new SkillRequirement(Skill.SLAYER, 38));
		return req;
	}
}
