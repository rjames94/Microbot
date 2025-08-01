/*
 * Copyright (c) 2019, Trevor <https://github.com/Trevor159>
 * Copyright (c) 2020, Zoinkwiz <https://github.com/Zoinkwiz>
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
package net.runelite.client.plugins.microbot.questhelper.helpers.quests.ernestthechicken;

import net.runelite.client.plugins.microbot.questhelper.panel.PanelDetails;
import net.runelite.client.plugins.microbot.questhelper.questhelpers.BasicQuestHelper;
import net.runelite.client.plugins.microbot.questhelper.requirements.ChatMessageRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.Requirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.conditional.Conditions;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.LogicType;
import net.runelite.client.plugins.microbot.questhelper.requirements.var.VarbitRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.Zone;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.ZoneRequirement;
import net.runelite.client.plugins.microbot.questhelper.rewards.ItemReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.QuestPointReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.UnlockReward;
import net.runelite.client.plugins.microbot.questhelper.steps.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;

import java.util.*;

public class ErnestTheChicken extends BasicQuestHelper
{
	//Items Required
	ItemRequirement spade, fishFood, poison, poisonedFishFood, pressureGauge, oilCan, rubberTube, key;

	Requirement inFirstFloor, inGroundFloor, inSecondFloor, killedFish, inSecretRoom, isLeverADown, isLeverBDown, isLeverCDown,
		isLeverDDown, isLeverEDown, isLeverFDown, isLeverAUp, isLeverBUp, isLeverCUp, isLeverDUp, isLeverEUp, isLeverFUp, inBasement, inRoomCD, inEmptyRoom;

	QuestStep talkToVeronica, enterManor, goToFirstFloor, pickupPoison, goToSecondFloor, pickupFishFood, usePoisonOnFishFood, goDownToGroundFloor,
		pickupSpade, searchCompost, useFishFoodOnFountain, searchFountain, enterManorWithKey, getTube, searchBookcase, goDownLadder, pullDownLeverA,
		pullDownLeverB, pullDownLeverC, pullDownLeverD, pullDownLeverE, pullDownLeverF, pullUpLeverA, pullUpLeverB, pullUpLeverC, pullUpLeverD, pullUpLeverE,
		pickupOilCan, goUpFromBasement, pullLeverToLeave, goToFirstFloorToFinish, talkToOddenstein, talkToOddenteinAgain;

	ConditionalStep getGaugeAndTube, getCan;

	//Zones
	Zone manorGround1, secretRoom, manorGround3, firstFloor, secondFloor, basement, roomCD, emptyRoom;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		initializeRequirements();
		setupConditions();
		setupSteps();
		Map<Integer, QuestStep> steps = new HashMap<>();

		steps.put(0, talkToVeronica);

		getGaugeAndTube = new ConditionalStep(this, enterManor);
		getGaugeAndTube.addStep(new Conditions(key, pressureGauge, inGroundFloor), getTube);
		getGaugeAndTube.addStep(new Conditions(key, pressureGauge), enterManorWithKey);
		getGaugeAndTube.addStep(new Conditions(killedFish, key), searchFountain);
		getGaugeAndTube.addStep(new Conditions(poisonedFishFood, key), useFishFoodOnFountain);
		getGaugeAndTube.addStep(new Conditions(poisonedFishFood, spade), searchCompost);
		getGaugeAndTube.addStep(new Conditions(poisonedFishFood), pickupSpade);
		getGaugeAndTube.addStep(new Conditions(fishFood, poison), usePoisonOnFishFood);
		getGaugeAndTube.addStep(new Conditions(inGroundFloor, fishFood), pickupPoison);
		getGaugeAndTube.addStep(new Conditions(inFirstFloor, fishFood), goDownToGroundFloor);
		getGaugeAndTube.addStep(inFirstFloor, pickupFishFood);
		getGaugeAndTube.addStep(inGroundFloor, goToFirstFloor);
		getGaugeAndTube.setLockingCondition(new Conditions(pressureGauge, rubberTube));

		getCan = new ConditionalStep(this, enterManor);
		getCan.addStep(new Conditions(isLeverADown, isLeverEDown), pullUpLeverE);
		getCan.addStep(new Conditions(isLeverBDown, isLeverCDown, isLeverDDown, isLeverFDown, isLeverEDown), pullUpLeverB);
		getCan.addStep(new Conditions(isLeverEDown, isLeverFDown, isLeverDUp), pullUpLeverD);
		getCan.addStep(new Conditions(new Conditions(LogicType.NAND, inRoomCD), isLeverBUp, isLeverCDown, isLeverFUp), pullDownLeverF);
		getCan.addStep(new Conditions(isLeverFDown, isLeverEDown, isLeverCDown), pullUpLeverE);
		getCan.addStep(new Conditions(isLeverFDown, isLeverEUp, isLeverCDown), pickupOilCan);
		getCan.addStep(new Conditions(isLeverEDown, isLeverFDown), pullDownLeverC);
		getCan.addStep(new Conditions(inRoomCD, isLeverCDown), pullUpLeverC);
		getCan.addStep(new Conditions(new Conditions(LogicType.OR, inRoomCD, inEmptyRoom), isLeverDUp), pullDownLeverD);
		getCan.addStep(new Conditions(isLeverBDown, isLeverDDown), pullUpLeverB);
		getCan.addStep(new Conditions(isLeverADown, isLeverDDown), pullUpLeverA);
		getCan.addStep(new Conditions(isLeverDDown, isLeverEDown), pullDownLeverF);
		getCan.addStep(new Conditions(isLeverDDown), pullDownLeverE);
		getCan.addStep(new Conditions(isLeverADown, isLeverBDown), pullDownLeverD);
		getCan.addStep(new Conditions(isLeverADown), pullDownLeverB);
		getCan.addStep(inBasement, pullDownLeverA);
		getCan.addStep(inSecretRoom, goDownLadder);
		getCan.addStep(inGroundFloor, searchBookcase);
		getCan.setLockingCondition(oilCan);

		ConditionalStep initialOddensteinConversation = new ConditionalStep(this, goToFirstFloorToFinish);
		initialOddensteinConversation.addStep(inSecondFloor, talkToOddenstein);
		initialOddensteinConversation.addStep(inFirstFloor, goToSecondFloor);
		initialOddensteinConversation.addStep(inSecretRoom, pullLeverToLeave);
		initialOddensteinConversation.addStep(inBasement, goUpFromBasement);

		ConditionalStep finishOddensteinConversation = new ConditionalStep(this, goToFirstFloorToFinish);
		finishOddensteinConversation.addStep(inSecondFloor, talkToOddenteinAgain);
		finishOddensteinConversation.addStep(inFirstFloor, goToSecondFloor);
		finishOddensteinConversation.addStep(inSecretRoom, pullLeverToLeave);
		finishOddensteinConversation.addStep(inBasement, goUpFromBasement);

		ConditionalStep getAllItems = new ConditionalStep(this, getGaugeAndTube);
		getAllItems.addStep(new Conditions(pressureGauge, rubberTube, oilCan), initialOddensteinConversation);
		getAllItems.addStep(new Conditions(pressureGauge, rubberTube), getCan);

		ConditionalStep completeQuest = new ConditionalStep(this, getGaugeAndTube);
		completeQuest.addStep(new Conditions(pressureGauge, rubberTube, oilCan), finishOddensteinConversation);
		completeQuest.addStep(new Conditions(pressureGauge, rubberTube), getCan);

		steps.put(1, getAllItems);
		steps.put(2, completeQuest);

		return steps;
	}

	@Override
	protected void setupRequirements()
	{
		spade = new ItemRequirement("Spade", ItemID.SPADE).isNotConsumed();
		fishFood = new ItemRequirement("Fish food", ItemID.FISH_FOOD);
		fishFood.setHighlightInInventory(true);
		poison = new ItemRequirement("Poison", ItemID.POISON);
		poison.setHighlightInInventory(true);
		poisonedFishFood = new ItemRequirement("Poisoned fish food", ItemID.POISONED_FISH_FOOD);
		poisonedFishFood.setHighlightInInventory(true);
		oilCan = new ItemRequirement("Oil can", ItemID.OIL_CAN);
		pressureGauge = new ItemRequirement("Pressure gauge", ItemID.PRESSURE_GAUGE);
		rubberTube = new ItemRequirement("Rubber tube", ItemID.RUBBER_TUBE);
		key = new ItemRequirement("Key", ItemID.CLOSET_KEY);
	}

	public void setupConditions()
	{
		inFirstFloor = new ZoneRequirement(firstFloor);
		inSecondFloor = new ZoneRequirement(secondFloor);
		inGroundFloor = new ZoneRequirement(manorGround1, secretRoom, manorGround3);
		inSecretRoom = new ZoneRequirement(secretRoom);
		killedFish = new ChatMessageRequirement("... then die and float to the surface.");
		isLeverADown = new VarbitRequirement(1788, 1);
		isLeverBDown = new VarbitRequirement(1789, 1);
		isLeverCDown = new VarbitRequirement(1790, 1);
		isLeverDDown = new VarbitRequirement(1791, 1);
		isLeverEDown = new VarbitRequirement(1792, 1);
		isLeverFDown = new VarbitRequirement(1793, 1);
		isLeverAUp = new VarbitRequirement(1788, 0);
		isLeverBUp = new VarbitRequirement(1789, 0);
		isLeverCUp = new VarbitRequirement(1790, 0);
		isLeverDUp = new VarbitRequirement(1791, 0);
		isLeverEUp = new VarbitRequirement(1792, 0);
		isLeverFUp = new VarbitRequirement(1793, 0);
		inBasement = new ZoneRequirement(basement);
		inRoomCD = new ZoneRequirement(roomCD);
		inEmptyRoom = new ZoneRequirement(emptyRoom);
	}

	@Override
	protected void setupZones()
	{
		manorGround1 = new Zone(new WorldPoint(3097, 3354, 0), new WorldPoint(3119, 3373, 0));
		secretRoom = new Zone(new WorldPoint(3090, 3354, 0), new WorldPoint(3096, 3363, 0));
		manorGround3 = new Zone(new WorldPoint(3120, 3354, 0), new WorldPoint(3126, 3360, 0));
		firstFloor = new Zone(new WorldPoint(3090, 3350, 1), new WorldPoint(3126, 3374, 1));
		secondFloor = new Zone(new WorldPoint(3090, 3350, 2), new WorldPoint(3126, 3374, 2));
		basement = new Zone(new WorldPoint(3090, 9745, 0), new WorldPoint(3118, 9767, 0));

		roomCD = new Zone(new WorldPoint(3105, 9767, 0), new WorldPoint(3112, 9758, 0));
		emptyRoom = new Zone(new WorldPoint(3100, 9762, 0), new WorldPoint(3104, 9758, 0));
	}

	public void setupSteps()
	{
		talkToVeronica = new NpcStep(this, NpcID.VERONICA, new WorldPoint(3109, 3329, 0), "Talk to Veronica outside of Draynor Manor.");
		talkToVeronica.addDialogStep("Aha, sounds like a quest. I'll help.");
		enterManor = new ObjectStep(this, ObjectID.HAUNTEDDOORL, new WorldPoint(3108, 3353, 0), "Enter Draynor Manor's ground floor.");
		goToFirstFloor = new ObjectStep(this, ObjectID.DRAYNOR_MANOR_STAIRS_UP, new WorldPoint(3109, 3364, 0), "Go upstairs and pick up the fish food.");
		pickupFishFood = new DetailedQuestStep(this, new WorldPoint(3108, 3356, 1), "Pick up the fish food in the south room.", fishFood);
		goDownToGroundFloor = new ObjectStep(this, ObjectID.DRAYNOR_MANOR_STAIRS_DOWN, new WorldPoint(3109, 3365, 1),
			"Go back downstairs and pick up the poison from the north west room.");

		pickupPoison = new DetailedQuestStep(this, new WorldPoint(3097, 3366, 0), "Pick up the poison from the north west room.", poison);
		usePoisonOnFishFood = new DetailedQuestStep(this, "Use the poison on the fish food", poison, fishFood);
		pickupSpade = new DetailedQuestStep(this, new WorldPoint(3120, 3359, 0), "Pick up the spade in the east room. Exit the manor through the door in the same room.", spade);
		searchCompost = new ObjectStep(this, ObjectID.HAUNTEDCOMPOSTHEAP, new WorldPoint(3085, 3361, 0), "Search the compost heap west of the manor.", spade);
		useFishFoodOnFountain = new ObjectStep(this, ObjectID.HAUNTEDFOUNTAIN, new WorldPoint(3088, 3335, 0),
			"Use the poisoned fish food on the fountain south west of the manor.", poisonedFishFood);
		searchFountain = new ObjectStep(this, ObjectID.HAUNTEDFOUNTAIN, new WorldPoint(3088, 3335, 0), "Search the fountain.");
		enterManorWithKey = new ObjectStep(this, ObjectID.HAUNTEDDOORL, new WorldPoint(3108, 3353, 0), "Go back into the manor's ground floor.");
		getTube = new DetailedQuestStep(this, new WorldPoint(3111, 3367, 0), "Enter the small room north of the staircase with the key and pick up the rubber tube.", rubberTube);
		searchBookcase = new ObjectStep(this, ObjectID.HAUNTEDBOOKCASEL, new WorldPoint(3097, 3358, 0), "Search the bookcase in the west room to enter the secret room.");
		goDownLadder = new ObjectStep(this, ObjectID.PUZZLE_LADDER_TOP, new WorldPoint(3092, 3362, 0), "Climb down the ladder into the basement.");

		pullDownLeverA = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERA, new WorldPoint(3108, 9745, 0), "Pull down lever A."));
		pullDownLeverB = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERB, new WorldPoint(3118, 9752, 0), "Pull down lever B.")).withNoHelpHiddenInSidebar(true);
		pullDownLeverC = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERC, new WorldPoint(3112, 9760, 0), "Pull down lever C.")).withNoHelpHiddenInSidebar(true);
		pullDownLeverD = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERD, new WorldPoint(3108, 9767, 0), "Pull down lever D.")).withNoHelpHiddenInSidebar(true);
		pullDownLeverE = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERE, new WorldPoint(3097, 9767, 0), "Pull down lever E.")).withNoHelpHiddenInSidebar(true);
		pullDownLeverF = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERF, new WorldPoint(3096, 9765, 0), "Pull down lever F.")).withNoHelpHiddenInSidebar(true);
		pullUpLeverA = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERA, new WorldPoint(3108, 9745, 0), "Pull up lever A.")).withNoHelpHiddenInSidebar(true);
		pullUpLeverB = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERB, new WorldPoint(3118, 9752, 0), "Pull up lever B.")).withNoHelpHiddenInSidebar(true);
		pullUpLeverC = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERC, new WorldPoint(3112, 9760, 0), "Pull up lever C.")).withNoHelpHiddenInSidebar(true);
		pullUpLeverD = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERD, new WorldPoint(3108, 9767, 0), "Pull up lever D.")).withNoHelpHiddenInSidebar(true);
		pullUpLeverE = new PuzzleWrapperStep(this, new ObjectStep(this, ObjectID.LEVERE, new WorldPoint(3097, 9767, 0), "Pull up lever E.")).withNoHelpHiddenInSidebar(true);
		pickupOilCan = new DetailedQuestStep(this, new WorldPoint(3092, 9755, 0), "Pick up the oil can in the west room.", oilCan);

		goUpFromBasement = new ObjectStep(this, ObjectID.PUZZLE_LADDER, new WorldPoint(3117, 9754, 0), "Climb out of the basement.");
		pullLeverToLeave = new ObjectStep(this, ObjectID.HAUNTEDLEVERUP, new WorldPoint(3096, 3357, 0), "Pull the lever to leave the secret room");
		goToFirstFloorToFinish = new ObjectStep(this, ObjectID.DRAYNOR_MANOR_STAIRS_UP, new WorldPoint(3109, 3364, 0), "Bring all the items to Oddenstein on the top floor.", oilCan, rubberTube, pressureGauge);
		goToSecondFloor = new ObjectStep(this, ObjectID.DRAYNOR_SPIRALSTAIRS, new WorldPoint(3105, 3363, 1), "Bring all the items to Oddenstein on the top floor.", oilCan, rubberTube, pressureGauge);
		talkToOddenstein = new NpcStep(this, NpcID.PROFESSOR_ODDENSTEIN, new WorldPoint(3116, 3364, 2), "Talk to Professor Oddenstein on the top floor.");
		talkToOddenstein.addDialogStep("I'm looking for a guy called Ernest.");
		talkToOddenstein.addDialogStep("Change him back this instant!");
		talkToOddenstein.addSubSteps(goUpFromBasement, pullLeverToLeave, goToFirstFloorToFinish, goToSecondFloor);
		talkToOddenteinAgain = new NpcStep(this, NpcID.PROFESSOR_ODDENSTEIN, new WorldPoint(3116, 3364, 2), "Give Professor Oddenstein the items to finish.", oilCan, rubberTube, pressureGauge);
	}

	@Override
	public List<String> getCombatRequirements()
	{
		ArrayList<String> reqs = new ArrayList<>();
		reqs.add("Able to survive a skeleton (level 22) attacking you");
		return reqs;
	}

	@Override
	public QuestPointReward getQuestPointReward()
	{
		return new QuestPointReward(4);
	}

	@Override
	public List<ItemReward> getItemRewards()
	{
		return Collections.singletonList(new ItemReward("Coins", ItemID.COINS, 300));
	}

	@Override
	public List<UnlockReward> getUnlockRewards()
	{
		return Arrays.asList(
				new UnlockReward("Access to the Killerwatt plane (Members Only)."),
				new UnlockReward("Ability to be assigned Killerwatts as a Slayer task."));
	}

	@Override
	public List<PanelDetails> getPanels()
	{
		List<PanelDetails> allSteps = new ArrayList<>();

		allSteps.add(new PanelDetails("Starting off", Collections.singletonList(talkToVeronica)));

		PanelDetails getGaugeAndTubePanel = new PanelDetails("Get the Gauge and Tube",
			Arrays.asList(enterManor, goToFirstFloor, pickupFishFood, goDownToGroundFloor, pickupPoison, usePoisonOnFishFood,
				pickupSpade, searchCompost, useFishFoodOnFountain, searchFountain, enterManorWithKey, getTube));
		getGaugeAndTubePanel.setLockingStep(getGaugeAndTube);
		allSteps.add(getGaugeAndTubePanel);

		PanelDetails getCanPanel = new PanelDetails("Get the oil can",
			Arrays.asList(searchBookcase, goDownLadder, pullDownLeverA, pullDownLeverB, pullDownLeverD, pullUpLeverB, pullUpLeverA, pullDownLeverF, pullDownLeverE, pullDownLeverC, pullUpLeverE, pickupOilCan));
		getCanPanel.setLockingStep(getCan);
		allSteps.add(getCanPanel);

		allSteps.add(new PanelDetails("Bring items to Oddenstein", Arrays.asList(talkToOddenstein, talkToOddenteinAgain)));
		return allSteps;
	}
}
