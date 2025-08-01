/*
 * Copyright (c) 2022, rileyyy <https://github.com/rileyyy/> and Obasill <https://github.com/obasill/>
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

package net.runelite.client.plugins.microbot.questhelper.helpers.achievementdiaries.kourend;

import net.runelite.client.plugins.microbot.questhelper.bank.banktab.BankSlotIcons;
import net.runelite.client.plugins.microbot.questhelper.collections.ItemCollections;
import net.runelite.client.plugins.microbot.questhelper.panel.PanelDetails;
import net.runelite.client.plugins.microbot.questhelper.questhelpers.ComplexStateQuestHelper;
import net.runelite.client.plugins.microbot.questhelper.questinfo.QuestHelperQuest;
import net.runelite.client.plugins.microbot.questhelper.requirements.Requirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.conditional.Conditions;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.player.SkillRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.quest.QuestRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.LogicType;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.Operation;
import net.runelite.client.plugins.microbot.questhelper.requirements.var.VarbitRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.var.VarplayerRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.Zone;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.ZoneRequirement;
import net.runelite.client.plugins.microbot.questhelper.rewards.ItemReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.UnlockReward;
import net.runelite.client.plugins.microbot.questhelper.steps.*;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.*;
import net.runelite.client.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KourendMedium extends ComplexStateQuestHelper
{
	// Items required
	ItemRequirement dramenStaff, kharedstsMemoirs, pickaxe, faceMask, hammer, nails, planks, kingWorm, axe, tinderbox,
		boxTrap, intelligence;

	// Items recommended
	ItemRequirement knife, warmClothing, combatGear, food, antipoison, radasBlessing1;

	// Quests required
	Requirement fairytaleII, depthsOfDespair, queenOfThieves, taleOfTheRighteous, forsakenTower, ascentOfArceuus,
		eaglesPeak, memoirHos, memoirShay, memoirPis, memoirLova,
		memoirArc;

	// Requirements
	Requirement notFairyRing, notKillLizardman, notTravelWithMemoirs, notMineSulphur, notEnterFarmingGuild,
		notSwitchSpellbooks, notRepairCrane, notDeliverIntelligence, notCatchBluegill, notUseBoulderShortcut,
		notSubdueWintertodt, notCatchChinchompa, notChopMahoganyTree, hasBird;

	QuestStep killGangBoss, travelFairyRing, travelWithMemoirs, repairCrane, catchBluegill, useBoulderShortcut,
		subdueWintertodt, catchChinchompa, chopMahoganyTree, claimReward, pickupWorms;

	ObjectStep mineSulphur, travelToMolchIsland, enterFarmingGuild;

	NpcStep killLizardman, switchSpellbooks, deliverIntelligence, talkToAlry;

	ZoneRequirement inMolchIsland;

	Zone molchIsland;

	ConditionalStep fairyRingTask, killLizardmanTask, travelWithMemoirsTask, mineSulphurTask, enterFarmingGuildTask,
		switchSpellbooksTask, repairCraneTask, deliverIntelligenceTask, catchBluegillTask, useBoulderShortcutTask,
		subdueWintertodtTask, catchChinchompaTask, chopMahoganyTreeTask;

	@Override
	public QuestStep loadStep()
	{
		initializeRequirements();
		setupSteps();

		ConditionalStep doMedium = new ConditionalStep(this, claimReward);

		fairyRingTask = new ConditionalStep(this, travelFairyRing);
		doMedium.addStep(notFairyRing, fairyRingTask);

		chopMahoganyTreeTask = new ConditionalStep(this, chopMahoganyTree);
		doMedium.addStep(notChopMahoganyTree, chopMahoganyTreeTask);

		enterFarmingGuildTask = new ConditionalStep(this, enterFarmingGuild);
		doMedium.addStep(notEnterFarmingGuild, enterFarmingGuildTask);

		catchBluegillTask = new ConditionalStep(this, travelToMolchIsland);
		catchBluegillTask.addStep(inMolchIsland, pickupWorms);
		catchBluegillTask.addStep(new Conditions(inMolchIsland, kingWorm), talkToAlry);
		catchBluegillTask.addStep(new Conditions(inMolchIsland, kingWorm, hasBird), catchBluegill);
		doMedium.addStep(notCatchBluegill, catchBluegillTask);

		mineSulphurTask = new ConditionalStep(this, mineSulphur);
		doMedium.addStep(notMineSulphur, mineSulphurTask);

		killLizardmanTask = new ConditionalStep(this, killLizardman);
		doMedium.addStep(notKillLizardman, killLizardmanTask);

		catchChinchompaTask = new ConditionalStep(this, catchChinchompa);
		doMedium.addStep(notCatchChinchompa, catchChinchompaTask);

		repairCraneTask = new ConditionalStep(this, repairCrane);
		doMedium.addStep(notRepairCrane, repairCraneTask);

		switchSpellbooksTask = new ConditionalStep(this, switchSpellbooks);
		doMedium.addStep(notSwitchSpellbooks, switchSpellbooksTask);

		useBoulderShortcutTask = new ConditionalStep(this, useBoulderShortcut);
		doMedium.addStep(notUseBoulderShortcut, useBoulderShortcutTask);

		subdueWintertodtTask = new ConditionalStep(this, subdueWintertodt);
		doMedium.addStep(notSubdueWintertodt, subdueWintertodtTask);

		deliverIntelligenceTask = new ConditionalStep(this, killGangBoss);
		deliverIntelligenceTask.addStep(intelligence.alsoCheckBank(questBank), deliverIntelligence);
		doMedium.addStep(notDeliverIntelligence, deliverIntelligenceTask);

		travelWithMemoirsTask = new ConditionalStep(this, travelWithMemoirs);
		doMedium.addStep(notTravelWithMemoirs, travelWithMemoirsTask);

		return doMedium;
	}

	@Override
	protected void setupRequirements()
	{
		notFairyRing = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 25);
		notKillLizardman = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 13);
		notTravelWithMemoirs = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 14);
		notMineSulphur = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 15);
		notEnterFarmingGuild = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 21);
		notSwitchSpellbooks = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 16);
		notRepairCrane = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 17);
		notDeliverIntelligence = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 18);
		notCatchBluegill = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 19);
		notUseBoulderShortcut = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 22);
		notSubdueWintertodt = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 20);
		notCatchChinchompa = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 23);
		notChopMahoganyTree = new VarplayerRequirement(VarPlayerID.KOUREND_ACHIEVEMENT_DIARY, false, 24);

		memoirArc = new VarbitRequirement(VarbitID.KOUREND_DIARY_ARC_TELEPORT, Operation.EQUAL, 0, "");
		memoirHos = new VarbitRequirement(VarbitID.KOUREND_DIARY_HOS_TELEPORT, Operation.EQUAL, 0, "");
		memoirLova = new VarbitRequirement(VarbitID.KOUREND_DIARY_LOVA_TELEPORT, Operation.EQUAL, 0, "");
		memoirShay = new VarbitRequirement(VarbitID.KOUREND_DIARY_SHAY_TELEPORT, Operation.EQUAL, 0, "");
		memoirPis = new VarbitRequirement(VarbitID.KOUREND_DIARY_PISC_TELEPORT, Operation.EQUAL, 0, "");

		// Required items
		dramenStaff = new ItemRequirement("Dramen or Lunar staff", ItemCollections.FAIRY_STAFF, 1, true)
			.showConditioned(notFairyRing).isNotConsumed();
		kharedstsMemoirs = new ItemRequirement("Kharedst's Memoirs or Book of the Dead",
			Arrays.asList(ItemID.BOOK_OF_THE_DEAD, ItemID.VEOS_KHAREDSTS_MEMOIRS)).showConditioned(notTravelWithMemoirs).isNotConsumed();
		pickaxe = new ItemRequirement("Any pickaxe", ItemCollections.PICKAXES).showConditioned(notMineSulphur).isNotConsumed();
		faceMask = new ItemRequirement("Facemask or slayer helmet", ItemCollections.SLAYER_HELMETS, 1, true)
			.showConditioned(notMineSulphur).isNotConsumed();
		faceMask.addAlternates(ItemID.SLAYER_FACEMASK, ItemID.GASMASK);
		hammer = new ItemRequirement("A hammer", ItemCollections.HAMMER).showConditioned(notRepairCrane).isNotConsumed();
		nails = new ItemRequirement("Nails", ItemCollections.NAILS, 50).showConditioned(notRepairCrane);
		planks = new ItemRequirement("Plank", ItemID.WOODPLANK, 3).showConditioned(notRepairCrane);
		kingWorm = new ItemRequirement("King worm or fish chunks", ItemID.KING_WORM).showConditioned(notCatchBluegill);
		kingWorm.addAlternates(ItemID.FISH_CHUNKS);
		kingWorm.setTooltip("Obtainable on Molch Island");
		axe = new ItemRequirement("Any axe", ItemCollections.AXES).showConditioned(new Conditions(LogicType.OR,
			notSubdueWintertodt, notChopMahoganyTree)).isNotConsumed();
		tinderbox = new ItemRequirement("Tinderbox", Arrays.asList(ItemID.WINT_TORCH, ItemID.TINDERBOX))
			.showConditioned(notSubdueWintertodt).isNotConsumed();
		boxTrap = new ItemRequirement("Box trap", ItemID.HUNTING_BOX_TRAP).showConditioned(notCatchChinchompa).isNotConsumed();
		intelligence = new ItemRequirement("Intelligence", ItemID.SHAYZIEN_GANG_INTELLIGENCE).showConditioned(notDeliverIntelligence);

		// Recommended items
		knife = new ItemRequirement("Knife", ItemID.KNIFE).showConditioned(notSubdueWintertodt).isNotConsumed();
		warmClothing = new ItemRequirement("Warm clothing", ItemCollections.WARM_CLOTHING, 4, true)
			.showConditioned(notSubdueWintertodt).isNotConsumed();
		combatGear = new ItemRequirement("Combat gear", -1, -1).isNotConsumed();
		combatGear.setDisplayItemId(BankSlotIcons.getCombatGear());
		food = new ItemRequirement("Food", ItemCollections.GOOD_EATING_FOOD, -1).showConditioned(notKillLizardman);
		antipoison = new ItemRequirement("Anti-poison", ItemCollections.ANTIPOISONS)
			.showConditioned(notKillLizardman);
		radasBlessing1 = new ItemRequirement("Rada's Blessing (1)", ItemID.ZEAH_BLESSING_EASY, -1)
			.showConditioned(notCatchChinchompa).isNotConsumed();

		// Required quests
		fairytaleII = new QuestRequirement(QuestHelperQuest.FAIRYTALE_II__CURE_A_QUEEN, QuestState.IN_PROGRESS);
		depthsOfDespair = new QuestRequirement(QuestHelperQuest.THE_DEPTHS_OF_DESPAIR, QuestState.FINISHED);
		queenOfThieves = new QuestRequirement(QuestHelperQuest.THE_QUEEN_OF_THIEVES, QuestState.FINISHED);
		taleOfTheRighteous = new QuestRequirement(QuestHelperQuest.TALE_OF_THE_RIGHTEOUS, QuestState.FINISHED);
		forsakenTower = new QuestRequirement(QuestHelperQuest.THE_FORSAKEN_TOWER, QuestState.FINISHED);
		ascentOfArceuus = new QuestRequirement(QuestHelperQuest.THE_ASCENT_OF_ARCEUUS, QuestState.FINISHED);
		eaglesPeak = new QuestRequirement(QuestHelperQuest.EAGLES_PEAK, QuestState.FINISHED);

		// Zone requirements
		inMolchIsland = new ZoneRequirement(molchIsland);

		hasBird = new VarbitRequirement(5983, 1);
	}

	@Override
	protected void setupZones()
	{
		molchIsland = new Zone(new WorldPoint(1360, 3640, 0), new WorldPoint(1376, 3625, 0));
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (memoirArc == null)
		{
			return;
		}

		travelWithMemoirs.resetDialogSteps();
		if (memoirArc.check(client))
		{
			travelWithMemoirs.addDialogStep("'A Dark Disposition' - Arceuus");
		}
		if (memoirHos.check(client))
		{
			travelWithMemoirs.addDialogStep("'Lunch by the Lancalliums' - Hosidius");
		}
		if (memoirLova.check(client))
		{
			travelWithMemoirs.addDialogStep("'Jewellery of Jubilation' - Lovakengj");
		}
		if (memoirShay.check(client))
		{
			travelWithMemoirs.addDialogStep("'History and Hearsay' - Shayzien");
		}
		if (memoirPis.check(client))
		{
			travelWithMemoirs.addDialogStep("'The Fisher's Flute' - Piscarilius");
		}
	}

	public void setupSteps()
	{
		// Travel to Fairy Ring
		travelFairyRing = new ObjectStep(this, ObjectID.POH_FAIRY_RING_LAST_AIP, new WorldPoint(2658, 3230, 0),
			"Travel from any fairy ring to south of Mount Karuulm (CIR).", dramenStaff.highlighted());

		// Kill a lizardman
		killLizardman = new NpcStep(this, NpcID.ZEAH_LIZARDMAN_1_VN, new WorldPoint(1507, 3683, 0),
			"Kill a lizardman.", true, combatGear, antipoison, food);
		killLizardman.addAlternateNpcs(NpcID.ZEAH_LIZARDMAN_1_VP, NpcID.ZEAH_LIZARDMAN_2_VN, NpcID.ZEAH_LIZARDMAN_2_VP,
			NpcID.ZEAH_LIZARDMAN_3_VN, NpcID.ZEAH_LIZARDMAN_3_VP);

		// Travel with Kharedst's Memoirs
		travelWithMemoirs = new ItemStep(this, "Teleport to each of the five cities via the memoirs.",
			kharedstsMemoirs.highlighted());

		// Mine some volcanic sulphur
		mineSulphur = new ObjectStep(this, ObjectID.SULPHUR_ROCK_01, new WorldPoint(1444, 3860, 0),
			"Mine some volcanic sulfur in Lovakengj.", true, pickaxe, faceMask.equipped());
		mineSulphur.addAlternateObjects(ObjectID.SULPHUR_ROCK_02, ObjectID.SULPHUR_ROCK_03);

		// Enter the farming guild
		enterFarmingGuild = new ObjectStep(this, ObjectID.KEBOS_FARMING_GUILD_DOOR_LEFT_CLOSED, new WorldPoint(1249, 3725, 0),
			"Enter the Farming Guild.", true);
		enterFarmingGuild.addAlternateObjects(ObjectID.KEBOS_FARMING_GUILD_DOOR_RIGHT_CLOSED);

		// Switch to the Arceuus spellbook via Tyss
		switchSpellbooks = new NpcStep(this, NpcID.ARCEUUS_DARKGUARDIAN, new WorldPoint(1712, 3882, 0),
			"Switch to the Arceuus spellbook via Tyss.");
		switchSpellbooks.addDialogStep("Can I try the magicks myself?");

		// Repair a crane
		repairCrane = new ObjectStep(this, ObjectID.PISCARILIUS_FISHINGCRANE_BROKEN, new WorldPoint(1830, 3735, 0),
			"Repair a crane within Port Piscarilius.", true, hammer, nails, planks);

		// Deliver some intelligence
		killGangBoss = new DetailedQuestStep(this,
			"Kill a gang boss or gang members for intelligence.", combatGear, food);
		deliverIntelligence = new NpcStep(this, NpcID.SHAYZIEN_CRIME_CONTROLLER, new WorldPoint(1504, 3632, 0),
			"Turn in the intelligence to Captain Ginea.", intelligence);
		deliverIntelligence.addAlternateNpcs(NpcID.AKD_GINEA_CUTSCENE);
		deliverIntelligence.addIcon(ItemID.SHAYZIEN_GANG_INTELLIGENCE);

		// Catch a bluegill on Lake Molch
		travelToMolchIsland = new ObjectStep(this, ObjectID.AERIAL_FISHING_BOAT, new WorldPoint(1405, 3611, 0),
			"Board the boaty to Molch Island.");
		travelToMolchIsland.addDialogStep("Molch Island");
		pickupWorms = new ItemStep(this, new WorldPoint(1371, 3633, 0), "Collect some King Worms.");
		talkToAlry = new NpcStep(this, NpcID.FISHING_NPC_ANGLER, new WorldPoint(1366, 3631, 0),
			"Talk to Alry the Angler to receive a bird.");
		talkToAlry.addDialogStep(2, "Could I have a go with your bird?");
		catchBluegill = new NpcStep(this, NpcID.FISHING_SPOT_AERIAL, new WorldPoint(1379, 3627, 0),
			"Catch a bluegill.", kingWorm);
		catchBluegill.addSubSteps(pickupWorms);

		// Use the boulder leap shortcut
		useBoulderShortcut = new ObjectStep(this, ObjectID.ARCHEUUS_RUNESTONE_SHORTCUT_BOULDER, new WorldPoint(1776, 3883, 0),
			"Use the boulder leap shortcut from the path to the Soul Altar.", new SkillRequirement(Skill.AGILITY, 49));

		// Subdue the Wintertodt
		subdueWintertodt = new ObjectStep(this, ObjectID.WINT_DOOR, new WorldPoint(1631, 3962, 0),
			"Subdue the Wintertodt (earn at least 500 points).", axe, tinderbox, food, warmClothing, knife, hammer);

		// Catch a chinchompa
		catchChinchompa = new DetailedQuestStep(this, new WorldPoint(1485, 3507, 0),
			"Catch a chinchompa in the Kourend Woodland.", boxTrap.highlighted());
		catchChinchompa.addIcon(ItemID.HUNTING_BOX_TRAP);

		// Chop some mahogany logs
		chopMahoganyTree = new ObjectStep(this, ObjectID.MAHOGANYTREE, new WorldPoint(1238, 3771, 0),
			"Chop some logs from a mahogany tree North of the Farming Guild.", true, axe);

		// Claim reward
		claimReward = new NpcStep(this, NpcID.ELISE_KOUREND_KEBOS_DIARY, new WorldPoint(1647, 3665, 0),
			"Talk to Elise in the Kourend castle courtyard to claim your reward!");
		claimReward.addDialogStep("I have a question about my Achievement Diary");
	}

	@Override
	public List<String> getCombatRequirements()
	{
		return Collections.singletonList("Kill a Lizardman (level 53)");
	}

	@Override
	public List<ItemRequirement> getItemRequirements()
	{
		return Arrays.asList(dramenStaff, kharedstsMemoirs, pickaxe, faceMask, hammer, nails.quantity(50),
			planks.quantity(3), kingWorm, axe, tinderbox, boxTrap);
	}

	@Override
	public List<ItemRequirement> getItemRecommended()
	{
		return Arrays.asList(knife, warmClothing, combatGear, food, antipoison, radasBlessing1);
	}

	@Override
	public List<Requirement> getGeneralRequirements()
	{
		ArrayList<Requirement> req = new ArrayList<>();

		req.add(new SkillRequirement(Skill.AGILITY, 49, true));
		req.add(new SkillRequirement(Skill.CRAFTING, 30, true));
		req.add(new SkillRequirement(Skill.FARMING, 45, true));
		req.add(new SkillRequirement(Skill.FIREMAKING, 50, false));
		req.add(new SkillRequirement(Skill.FISHING, 43, false));
		req.add(new SkillRequirement(Skill.HUNTER, 53, true));
		req.add(new SkillRequirement(Skill.MINING, 42, true));
		req.add(new SkillRequirement(Skill.WOODCUTTING, 50, true));

		req.add(fairytaleII);
		req.add(depthsOfDespair);
		req.add(queenOfThieves);
		req.add(taleOfTheRighteous);
		req.add(forsakenTower);
		req.add(ascentOfArceuus);
		req.add(eaglesPeak);

		return req;
	}

	@Override
	public List<ItemReward> getItemRewards()
	{
		return Arrays.asList(
			new ItemReward("Rada's Blessing (2)", ItemID.ZEAH_BLESSING_MEDIUM, 1),
			new ItemReward("7,500 Exp. Lamp (Any skill over 40)", ItemID.THOSF_REWARD_LAMP, 1));
	}

	@Override
	public List<UnlockReward> getUnlockRewards()
	{
		return Arrays.asList(
			new UnlockReward("Free access cost for Crabclaw Isle"),
			new UnlockReward("5% chance to mine two Dense essence blocks at once"),
			new UnlockReward("20 free Dynamite per day from Thirus"),
			new UnlockReward("Reduced tanning prices at Eodan in Forthos Dungeon to 60%."));
	}

	@Override
	public List<PanelDetails> getPanels()
	{
		List<PanelDetails> allSteps = new ArrayList<>();

		PanelDetails fairyRingStep = new PanelDetails("Fairy Ring To Mount Karuulm",
			Collections.singletonList(travelFairyRing), dramenStaff, fairytaleII);
		fairyRingStep.setDisplayCondition(notFairyRing);
		fairyRingStep.setLockingStep(fairyRingTask);
		allSteps.add(fairyRingStep);

		PanelDetails chopMahoganyStep = new PanelDetails("Chop Mahogany Tree",
			Collections.singletonList(chopMahoganyTree), new SkillRequirement(Skill.WOODCUTTING, 50, true), axe);
		chopMahoganyStep.setDisplayCondition(notChopMahoganyTree);
		chopMahoganyStep.setLockingStep(chopMahoganyTreeTask);
		allSteps.add(chopMahoganyStep);

		PanelDetails enterFarmingGuildStep = new PanelDetails("Enter The Farming Guild",
			Collections.singletonList(enterFarmingGuild), new SkillRequirement(Skill.FARMING, 45, true));
		enterFarmingGuildStep.setDisplayCondition(notEnterFarmingGuild);
		enterFarmingGuildStep.setLockingStep(enterFarmingGuildTask);
		allSteps.add(enterFarmingGuildStep);

		PanelDetails catchBluegillStep = new PanelDetails("Catch A Bluegill", Arrays.asList(travelToMolchIsland,
			pickupWorms, talkToAlry, catchBluegill), new SkillRequirement(Skill.FISHING, 43, false),
			new SkillRequirement(Skill.HUNTER, 35, false), kingWorm);
		catchBluegillStep.setDisplayCondition(notCatchBluegill);
		catchBluegillStep.setLockingStep(catchBluegillTask);
		allSteps.add(catchBluegillStep);

		PanelDetails mineSulphurStep = new PanelDetails("Mine volcanic sulphur", Collections.singletonList(mineSulphur),
			new SkillRequirement(Skill.MINING, 42, true), pickaxe, faceMask);
		mineSulphurStep.setDisplayCondition(notMineSulphur);
		mineSulphurStep.setLockingStep(mineSulphurTask);
		allSteps.add(mineSulphurStep);

		PanelDetails killLizardmanStep = new PanelDetails("Kill A Lizardman", Collections.singletonList(killLizardman),
			food, combatGear, antipoison);
		killLizardmanStep.setDisplayCondition(notKillLizardman);
		killLizardmanStep.setLockingStep(killLizardmanTask);
		allSteps.add(killLizardmanStep);

		PanelDetails catchChinStep = new PanelDetails("Catch A Chinchompa", Collections.singletonList(catchChinchompa),
			new SkillRequirement(Skill.HUNTER, 53, true), boxTrap, eaglesPeak);
		catchChinStep.setDisplayCondition(notCatchChinchompa);
		catchChinStep.setLockingStep(catchChinchompaTask);
		allSteps.add(catchChinStep);

		PanelDetails repairCraneStep = new PanelDetails("Repair Crane", Collections.singletonList(repairCrane),
			new SkillRequirement(Skill.CRAFTING, 30, true), hammer, nails, planks);
		repairCraneStep.setDisplayCondition(notRepairCrane);
		repairCraneStep.setLockingStep(repairCraneTask);
		allSteps.add(repairCraneStep);

		PanelDetails switchSpellbookStep = new PanelDetails("Arceuus Spellbook", Collections.singletonList(switchSpellbooks));
		switchSpellbookStep.setDisplayCondition(notSwitchSpellbooks);
		switchSpellbookStep.setLockingStep(switchSpellbooksTask);
		allSteps.add(switchSpellbookStep);

		PanelDetails leapBoulderStep = new PanelDetails("Leap the boulder", Collections.singletonList(useBoulderShortcut),
			new SkillRequirement(Skill.AGILITY, 49, true));
		leapBoulderStep.setDisplayCondition(notUseBoulderShortcut);
		leapBoulderStep.setLockingStep(useBoulderShortcutTask);
		allSteps.add(leapBoulderStep);

		PanelDetails subdueWintertodtStep = new PanelDetails("Subdue the Wintertodt",
			Collections.singletonList(subdueWintertodt), new SkillRequirement(Skill.FIREMAKING, 50, false), axe,
			tinderbox, food, warmClothing, knife, hammer);
		subdueWintertodtStep.setDisplayCondition(notSubdueWintertodt);
		subdueWintertodtStep.setLockingStep(subdueWintertodtTask);
		allSteps.add(subdueWintertodtStep);

		PanelDetails deliverIntelligenceStep = new PanelDetails("Deliver intelligence", Arrays.asList(killGangBoss,
			deliverIntelligence), combatGear, food);
		deliverIntelligenceStep.setDisplayCondition(notDeliverIntelligence);
		deliverIntelligenceStep.setLockingStep(deliverIntelligenceTask);
		allSteps.add(deliverIntelligenceStep);

		PanelDetails travelMemoirsStep = new PanelDetails("Travel With Kharedst's Memoirs",
			Collections.singletonList(travelWithMemoirs), depthsOfDespair, queenOfThieves,
			taleOfTheRighteous, forsakenTower, ascentOfArceuus, kharedstsMemoirs);
		travelMemoirsStep.setDisplayCondition(notTravelWithMemoirs);
		travelMemoirsStep.setLockingStep(travelWithMemoirsTask);
		allSteps.add(travelMemoirsStep);

		allSteps.add(new PanelDetails("Finishing off", Collections.singletonList(claimReward)));

		return allSteps;
	}
}
