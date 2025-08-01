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
package net.runelite.client.plugins.microbot.questhelper.helpers.quests.mourningsendpartii;

import net.runelite.client.plugins.microbot.questhelper.collections.ItemCollections;
import net.runelite.client.plugins.microbot.questhelper.collections.KeyringCollection;
import net.runelite.client.plugins.microbot.questhelper.panel.PanelDetails;
import net.runelite.client.plugins.microbot.questhelper.questhelpers.BasicQuestHelper;
import net.runelite.client.plugins.microbot.questhelper.questinfo.QuestHelperQuest;
import net.runelite.client.plugins.microbot.questhelper.requirements.Requirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.conditional.Conditions;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.ItemRequirements;
import net.runelite.client.plugins.microbot.questhelper.requirements.item.KeyringRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.quest.QuestRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.LogicType;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.Operation;
import net.runelite.client.plugins.microbot.questhelper.requirements.var.VarbitRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.widget.WidgetTextRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.Zone;
import net.runelite.client.plugins.microbot.questhelper.requirements.zone.ZoneRequirement;
import net.runelite.client.plugins.microbot.questhelper.rewards.ExperienceReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.ItemReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.QuestPointReward;
import net.runelite.client.plugins.microbot.questhelper.rewards.UnlockReward;
import net.runelite.client.plugins.microbot.questhelper.steps.*;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.*;

import java.util.*;

public class MourningsEndPartII extends BasicQuestHelper
{
	//Items Required
	ItemRequirement deathTalisman, deathTalismanHeader, mournersOutfit, gasMask, mournerTop, mournerTrousers, mournerBoots, mournerGloves, mournerCloak, chisel, rope, ropeHighlight, newKey, edernsJournal,
		blackenedCrystal, newlyMadeCrystal, newlyIfOneTrip, mirror, yellowCrystal, cyanCrystal, blueCrystal, fracturedCrystal, fracturedCrystal2, chargedCrystal, chargedCrystalHighlight, newlyMadeCrystalHighlight;

	// Items recommended
	ItemRequirement westArdougneTeleport, lletyaTeleport, staminaPotions, prayerPotions, food;

	Requirement inMournerBasement, inMournerHQ, inCave, inTempleF0, inTempleF1, inTempleF2, inTempleF2NorthRoom, inCaveOrF0, inTempleStairSquare, inNorthF2, inSouthF2,
		knowToUseCrystal, inBlueRoom, inCyanRoom, solvedPuzzle1, solvedPuzzle2, solvedPuzzle3, solvedPuzzle4, solvedPuzzle5, dispenserEmpty, inYellowRoom, usedRope,
		placedBlueCrystalInJumpRoom, blueCrystalNotPlaced, inCentralArea, inDeathAltarArea, inBehindBarrierCentralArea, enteredDeathArea, inIbanRoom,
		inPassF1, inPassF0, inWellEntrance, receivedList, inDeathAltar;

	Requirement f0r4c0SB,
		f0r1c2SG, f0r1c2U,
		f0r0c2EG,
		f0r0c4NB,
		cyanDoorOpen,
		f0r3c4W,
		f0r3c3F,
		f0r4c3U,
		f0r1c3F,
		f0r1c4U,
		redAtAltar, redAtDoor;

	Requirement f1r0c3EY, f1r0c3,
		f1r1c3SY,
		startColumnN,
		f1r3c2NC, f1r3c2NY,
		f1r3c3S, f1r3c3WC, f1r3c3WY, f1r3c3U,
		f1r3c4W, f1r3c4D,
		f1r4c2EC, f1r4c2EY,
		f1r4c3EG, f1r4c3UC, f1r4c3UY;

	Requirement f2r4c0DB, f2r4c0SR,
		f2r4c3WY, f2r4c3WC,
		f2r0c0DR,
		f2r2c0ER, f2r2c0SNotRed,
		f2r3c3S,
		f2r1c3F,
		f2r1c2D,
		f2r0c3E,
		f2r0c4D,
		f2r1c2LG,
		f2r1c4L,
		f2r1c3N, f2r3c3W,
		f2r3c2WB;

	DetailedQuestStep talkToArianwyn, enterMournerHQ, enterMournerBasement, talkToEssyllt, enterCave, searchCorpse, goUpStairsTemple, goUpSouthLadder, goToMiddleFromSouth, goUpFromMiddleToNorth, useChisel,
		bringCrystalToArianwyn, talkToElunedAfterGivingCrystal, talkToArianwynAfterGivingCrystal, goBackIntoMournerHQ, goBackIntoMournerBasement, goBackIntoMournerCave, enterTempleOfLight, goUpStairsTempleC1,
		genericGoDownToFloor1, genericGoDownToFloor0, genericGoUpToFloor1, genericGoUpToFloor2, pullDispenser1, puzzle1Pillar1, puzzle1Pillar2, puzzle1Pillar3, puzzle1Pillar4, puzzle1Pillar5,
		climbWallSupport, pullDispenser2, puzzle2Pillar1, puzzle2Pillar2, puzzle2Pillar3, puzzle2Pillar4, puzzle2Pillar5, puzzle2Pillar6, pullDispenser3, puzzle3Pillar1, puzzle3Pillar2, puzzle3Pillar3, puzzle3Pillar4,
		puzzle3Pillar5, puzzle3Pillar6, puzzle3Pillar6RemoveYellow, goUpLadderNorthForPuzzle3, puzzle3Pillar7, goDownFromF2NorthRoomPuzzle3, goUpToFloor2Puzzle3, puzzle3Pillar8, puzzle3Pillar9, goDownFromF2Puzzle3,
		goDownFromF1Puzzle3, enterNorthWestRoomPuzzle3, yellowRoomRotateToLeave, goUpToFirstFloorPuzzle4, pullDispenser4, puzzle4Pillar1, puzzle4Pillar2, puzzle4Pillar3, puzzle4Pillar4,
		puzzle4Pillar5, puzzle4Pillar6, puzzle4Pillar3RemoveCyan, goUpLadderNorthForPuzzle4, puzzle4Pillar7, goDownFromF2NorthRoomPuzzle4, goUpToFloor2Puzzle4, puzzle4Pillar8, puzzle4Pillar9, goDownFromF2Puzzle4,
		removeMirrorPuzzle4, goDownRope, goToGroundFloorPuzzle4, enterCyanDoor, climbUpRope, pullDispenser5, puzzle5Pillar1, puzzle5Pillar2, puzzle5Pillar6, puzzle5Pillar5, puzzle5Pillar4, puzzle5Pillar3,
		climbWallSupportPuzzle5, puzzle5Pillar5RemoveMirror, puzzle5Pillar3RotateUp, goUpToFloor2Puzzle5, goDownToMiddleFromSouthPuzzle5, goUpFromMiddleToNorthPuzzle5, puzzle5Pillar7, goDownToMiddleFromNorthPuzzle5,
		goUpFromMiddleToSouthPuzzle5, puzzle5Pillar8, puzzle5Pillar9, puzzle5Pillar10, puzzle5Pillar11, goDownFromF2Puzzle5, goDownFromF1Puzzle5, puzzle5Pillar12, puzzle5Pillar13, puzzle5Pillar14, resetPuzzle,
		pullDispenser6, puzzle6Pillar1, puzzle6Pillar2, goDownFromF1Puzzle6, puzzle6Pillar3, puzzle6Pillar4, puzzle6Pillar5, puzzle6Pillar6, puzzle6Pillar7, puzzle6Pillar8, goUpToF1Puzzle6, puzzle6Pillar9,
		goUpNorthLadderToF2Puzzle6, puzzle6Pillar10, goDownNorthLadderToF1Puzzle6, goUpToFloor2Puzzle6, puzzle6Pillar11, puzzle6Pillar12, puzzle6Pillar13, goDownToMiddleFromSouthPuzzle6, getDeathTalisman,
		goUpFromMiddleToNorthPuzzle6, puzzle6Pillar14, puzzle6Pillar15, puzzle6Pillar16, puzzle6Pillar17, goDownToMiddleFromNorthPuzzle6, goDownToCentre, turnKeyMirror, enterDeathAltarBarrier, enterUndergroundPass,
		enterWell, leavePassCentre, enterSouthPass, enterAltarFromBehind, getDeathTalismanInCentre, enterDeathAltar, turnKeyMirrorCharging, goUpToF1ForCharging, goUpToF2ForCharging, goDownToMiddleFromSouthCharging,
		goDownToCentreCharging, getDeathTalismanInCentreDoorCorrect, enterMournerHQCharging, enterMournerBasementCharging, enterMournerCaveCharging, useCrystalOnAltar, leaveDeathAltar, turnPillarFromTemple, goUpFromCentre,
		goUpToNorthToCharge, useCrystalOnCrystal, enterMournerHQCrystal, enterMournerBasementCrystal, enterMournerCaveCrystal, enterFloor1Crystal, enterFloor2Crystal, goDownToMiddleFromSouthCrystal, returnToArianwyn;

	ConditionalStep puzzle5PlaceBlue;

	ObjectStep searchBlueChest, searchMagentaChest, searchYellowChest, searchCyanChest, searchMagentaYellowChest;

	QuestStep useRope, goDownToEnd, goUpFromMiddleToSouth;

	//Zones
	Zone mournerHQ, mournerHQ2, mournerBasement, cave, templeF0, templeF1, northTempleF2, southTempleF2, northRoomF2, templeStairSquare, blueRoom, yellowRoom1, yellowRoom2, cyanRoom1,
		cyanRoom2, deathAltarArea, centralArea, centralAreaBehindBarrier, ibanRoom, wellEntrance, passF1, passF0, deathAltar;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		initializeRequirements();
		setupConditions();
		setupSteps();
		Map<Integer, QuestStep> steps = new HashMap<>();

		steps.put(0, talkToArianwyn);

		ConditionalStep goTalkToEssyllt = new ConditionalStep(this, enterMournerHQ);
		goTalkToEssyllt.addStep(inMournerBasement, talkToEssyllt);
		goTalkToEssyllt.addStep(inMournerHQ, enterMournerBasement);

		steps.put(5, goTalkToEssyllt);

		ConditionalStep getCrystal = new ConditionalStep(this, enterMournerHQ);
		getCrystal.addStep(blackenedCrystal.alsoCheckBank(questBank), bringCrystalToArianwyn);
		getCrystal.addStep(inNorthF2, useChisel);
		getCrystal.addStep(inTempleStairSquare, goUpFromMiddleToNorth);
		getCrystal.addStep(inSouthF2, goToMiddleFromSouth);
		getCrystal.addStep(inTempleF1, goUpSouthLadder);
		getCrystal.addStep(inCaveOrF0, goUpStairsTemple);
		getCrystal.addStep(inMournerBasement, enterCave);
		getCrystal.addStep(inMournerHQ, enterMournerBasement);

		steps.put(10, getCrystal);
		steps.put(15, getCrystal);
		steps.put(20, getCrystal);

		steps.put(30, talkToElunedAfterGivingCrystal);

		ConditionalStep puzzle1 = new ConditionalStep(this, goBackIntoMournerHQ);
		puzzle1.addStep(new Conditions(inBlueRoom, f1r0c3EY), searchBlueChest);
		puzzle1.addStep(new Conditions(inTempleF1, f1r0c3EY), climbWallSupport);
		puzzle1.addStep(new Conditions(inTempleF1, f1r1c3SY), puzzle1Pillar5);
		puzzle1.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c3S), puzzle1Pillar4);
		puzzle1.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c4W), puzzle1Pillar3);
		puzzle1.addStep(new Conditions(inTempleF1, dispenserEmpty, mirror, startColumnN), puzzle1Pillar2);
		puzzle1.addStep(new Conditions(inTempleF1, dispenserEmpty, mirror), puzzle1Pillar1);
		puzzle1.addStep(inTempleF1, pullDispenser1);
		puzzle1.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle1.addStep(inTempleF0, goUpStairsTempleC1);
		puzzle1.addStep(inCave, enterTempleOfLight);
		puzzle1.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle1.addStep(inMournerHQ, goBackIntoMournerBasement);
		puzzle1.setLockingCondition(solvedPuzzle1);

		ConditionalStep puzzle2 = new ConditionalStep(this, goBackIntoMournerHQ);
		puzzle2.addStep(new Conditions(inTempleF1, f1r4c3EG), searchMagentaChest);
		puzzle2.addStep(new Conditions(inTempleF1, f1r1c3SY), pullDispenser2);
		puzzle2.addStep(new Conditions(inTempleF1, f1r4c2EC), puzzle2Pillar6);
		puzzle2.addStep(new Conditions(inTempleF1, f1r3c2NC), puzzle2Pillar5);
		puzzle2.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c3WC), puzzle2Pillar4);
		puzzle2.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c4W), puzzle2Pillar3);
		puzzle2.addStep(new Conditions(inTempleF1, dispenserEmpty, startColumnN), puzzle2Pillar2);
		puzzle2.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle2Pillar1);
		puzzle2.addStep(inTempleF1, pullDispenser2);
		puzzle2.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle2.addStep(inTempleF0, genericGoUpToFloor1);
		puzzle2.addStep(inCave, enterTempleOfLight);
		puzzle2.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle2.addStep(inMournerHQ, goBackIntoMournerBasement);
		puzzle2.setLockingCondition(solvedPuzzle2);

		ConditionalStep puzzle3 = new ConditionalStep(this, goBackIntoMournerHQ);
		puzzle3.addStep(new Conditions(inYellowRoom, f0r4c0SB), searchYellowChest);
		puzzle3.addStep(new Conditions(inTempleF0, f0r4c0SB), pullDispenser3);
		puzzle3.addStep(new Conditions(inYellowRoom, f2r4c0DB), puzzle3Pillar9);
		puzzle3.addStep(new Conditions(inTempleF0, f2r4c0DB), enterNorthWestRoomPuzzle3);
		puzzle3.addStep(new Conditions(inTempleF1, f2r4c0DB), goDownFromF1Puzzle3);
		puzzle3.addStep(new Conditions(inTempleF2NorthRoom, f2r4c3WC), goDownFromF2NorthRoomPuzzle3);
		puzzle3.addStep(new Conditions(inTempleF2, f2r4c0DB), goDownFromF2Puzzle3);
		puzzle3.addStep(new Conditions(inTempleF2, f2r4c3WC), puzzle3Pillar8);
		puzzle3.addStep(new Conditions(inTempleF1, f2r4c3WC), goUpToFloor2Puzzle3);
		puzzle3.addStep(new Conditions(inTempleF2NorthRoom, f1r4c3UC), puzzle3Pillar7);
		puzzle3.addStep(new Conditions(inTempleF1, f1r4c3UC), goUpLadderNorthForPuzzle3);
		puzzle3.addStep(new Conditions(inTempleF1, f1r4c3EG), puzzle3Pillar6RemoveYellow);
		puzzle3.addStep(new Conditions(inTempleF1, f1r4c2EC), puzzle3Pillar6);
		puzzle3.addStep(new Conditions(inTempleF1, f1r3c2NC), puzzle3Pillar5);
		puzzle3.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c3WC), puzzle3Pillar4);
		puzzle3.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c4W), puzzle3Pillar3);
		puzzle3.addStep(new Conditions(inTempleF1, dispenserEmpty, startColumnN), puzzle3Pillar2);
		puzzle3.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle3Pillar1);
		puzzle3.addStep(inTempleF1, pullDispenser3);
		puzzle3.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle3.addStep(inTempleF0, genericGoUpToFloor1);
		puzzle3.addStep(inCave, enterTempleOfLight);
		puzzle3.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle3.addStep(inMournerHQ, goBackIntoMournerBasement);
		puzzle3.setLockingCondition(solvedPuzzle3);

		ConditionalStep puzzle4 = new ConditionalStep(this, goBackIntoMournerHQ);
		puzzle4.addStep(new Conditions(inCyanRoom, f2r0c0DR), searchCyanChest);
		puzzle4.addStep(new Conditions(inTempleF0, cyanDoorOpen), enterCyanDoor);
		puzzle4.addStep(new Conditions(inTempleF1, cyanDoorOpen), goToGroundFloorPuzzle4);
		puzzle4.addStep(new Conditions(inTempleF1, usedRope, f2r0c0DR), goDownRope);
		puzzle4.addStep(new Conditions(inTempleF1, f2r0c0DR), useRope);
		puzzle4.addStep(new Conditions(inTempleF2, f2r0c0DR), goDownFromF2Puzzle4);
		puzzle4.addStep(new Conditions(inTempleF2, f2r4c0SR, f2r2c0SNotRed), removeMirrorPuzzle4);
		puzzle4.addStep(new Conditions(inTempleF2, f2r4c0SR), puzzle4Pillar9);
		puzzle4.addStep(new Conditions(inYellowRoom, f0r4c0SB), yellowRoomRotateToLeave);
		puzzle4.addStep(new Conditions(inTempleF0), goUpToFirstFloorPuzzle4);
		puzzle4.addStep(new Conditions(inTempleF2, f2r4c3WY), puzzle4Pillar8); // Says to place even when placed
		puzzle4.addStep(new Conditions(inTempleF1, f2r4c3WY), goUpToFloor2Puzzle4);
		puzzle4.addStep(new Conditions(inTempleF2NorthRoom, f2r4c3WY), goDownFromF2NorthRoomPuzzle4);
		puzzle4.addStep(new Conditions(inTempleF2NorthRoom, f1r4c3UY), puzzle4Pillar7);
		puzzle4.addStep(new Conditions(inTempleF1, f1r4c3UY), goUpLadderNorthForPuzzle4);
		puzzle4.addStep(new Conditions(inTempleF1, f1r4c2EY), puzzle4Pillar6);
		puzzle4.addStep(new Conditions(inTempleF1, f1r3c2NY), puzzle4Pillar5);
		puzzle4.addStep(new Conditions(inTempleF1, f1r3c3WY), puzzle4Pillar4);
		puzzle4.addStep(new Conditions(inTempleF1, f1r3c3WC), puzzle4Pillar3RemoveCyan);
		puzzle4.addStep(new Conditions(inTempleF1, f1r3c4W), puzzle4Pillar3);
		puzzle4.addStep(new Conditions(inTempleF1, dispenserEmpty, startColumnN), puzzle4Pillar2);
		puzzle4.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle4Pillar1);
		puzzle4.addStep(inTempleF1, pullDispenser4);
		puzzle4.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle4.addStep(inTempleF0, genericGoUpToFloor1);
		puzzle4.addStep(inCave, enterTempleOfLight);
		puzzle4.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle4.addStep(inMournerHQ, goBackIntoMournerBasement);
		puzzle4.setLockingCondition(solvedPuzzle4);

		puzzle5PlaceBlue = new ConditionalStep(this, puzzle5Pillar5);
		puzzle5PlaceBlue.addStep(new Conditions(placedBlueCrystalInJumpRoom), puzzle5Pillar5RemoveMirror);
		puzzle5PlaceBlue.addStep(new Conditions(inBlueRoom, f1r0c3EY), puzzle5Pillar6);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, f1r0c3EY), climbWallSupportPuzzle5);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, f1r1c3SY), puzzle5Pillar5);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, f1r3c3S), puzzle5Pillar4);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, f1r3c3WY), pullDispenser5);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, dispenserEmpty, f1r3c4W), puzzle5Pillar3);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, dispenserEmpty, startColumnN), puzzle5Pillar2);
		puzzle5PlaceBlue.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle5Pillar1);
		puzzle5PlaceBlue.addStep(inTempleF1, pullDispenser5);
		puzzle5PlaceBlue.addStep(inCyanRoom, climbUpRope);
		puzzle5PlaceBlue.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle5PlaceBlue.addStep(inTempleF0, genericGoUpToFloor1);
		puzzle5PlaceBlue.addStep(inCave, enterTempleOfLight);
		puzzle5PlaceBlue.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle5PlaceBlue.addStep(inMournerHQ, goBackIntoMournerBasement);
		puzzle5PlaceBlue.setLockingCondition(placedBlueCrystalInJumpRoom);

		ConditionalStep puzzle5Part2 = new ConditionalStep(this, goBackIntoMournerHQ);
		puzzle5Part2.addStep(new Conditions(inTempleF0, f0r0c2EG, f0r0c4NB), searchMagentaYellowChest);
		puzzle5Part2.addStep(new Conditions(inTempleF0, f2r1c3F, f0r0c2EG, f2r0c4D), puzzle5Pillar14);
		puzzle5Part2.addStep(new Conditions(inTempleF0, f2r1c3F, f0r1c2SG, f2r0c4D), puzzle5Pillar13);
		puzzle5Part2.addStep(new Conditions(inTempleF0, f2r1c3F, f2r1c2D, f2r0c4D), puzzle5Pillar12);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f2r1c3F, f2r1c2D, f2r0c4D), goDownFromF1Puzzle5);
		puzzle5Part2.addStep(new Conditions(inTempleF2, f2r1c3F, f2r1c2D, f2r0c4D), goDownFromF2Puzzle5);
		puzzle5Part2.addStep(blueCrystalNotPlaced, resetPuzzle);
		puzzle5Part2.addStep(new Conditions(inSouthF2, f2r1c3F, f2r1c2D, f2r0c3E), puzzle5Pillar11);
		puzzle5Part2.addStep(new Conditions(inSouthF2, f2r1c3F, f2r1c2D), puzzle5Pillar10);
		puzzle5Part2.addStep(new Conditions(inSouthF2, f2r1c3F), puzzle5Pillar9);
		puzzle5Part2.addStep(new Conditions(inSouthF2, f2r3c3S), puzzle5Pillar8);
		puzzle5Part2.addStep(new Conditions(inTempleStairSquare, f2r3c3S), goUpFromMiddleToSouthPuzzle5);
		puzzle5Part2.addStep(new Conditions(inNorthF2, f2r3c3S), goDownToMiddleFromNorthPuzzle5);
		puzzle5Part2.addStep(new Conditions(inNorthF2, f1r3c3U), puzzle5Pillar7);
		puzzle5Part2.addStep(new Conditions(inTempleStairSquare, f1r3c3U), goUpFromMiddleToNorthPuzzle5);
		puzzle5Part2.addStep(new Conditions(inSouthF2, f1r3c3U), goDownToMiddleFromSouthPuzzle5);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f1r3c3U), goUpToFloor2Puzzle5);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f1r0c3), puzzle5Pillar5RemoveMirror);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f1r1c3SY), puzzle5Pillar3RotateUp);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f1r3c3S), puzzle5Pillar4);
		puzzle5Part2.addStep(new Conditions(inTempleF1, f1r3c4W), puzzle5Pillar3);
		puzzle5Part2.addStep(new Conditions(inTempleF1, startColumnN), puzzle5Pillar2);
		puzzle5Part2.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle5Pillar1);
		puzzle5Part2.addStep(inTempleF1, pullDispenser5);
		puzzle5Part2.addStep(inTempleF2, genericGoDownToFloor1);
		puzzle5Part2.addStep(inTempleF0, genericGoUpToFloor1);
		puzzle5Part2.addStep(inCave, enterTempleOfLight);
		puzzle5Part2.addStep(inMournerBasement, goBackIntoMournerCave);
		puzzle5Part2.addStep(inMournerHQ, goBackIntoMournerBasement);

		ConditionalStep deathAltarPuzzle = new ConditionalStep(this, goBackIntoMournerHQ);
		deathAltarPuzzle.addStep(new Conditions(inCentralArea, redAtAltar, f2r1c2LG, f2r3c2WB), enterDeathAltarBarrier);
		deathAltarPuzzle.addStep(new Conditions(inCentralArea, redAtDoor, f2r1c2LG, f2r3c2WB), turnKeyMirror);
		deathAltarPuzzle.addStep(new Conditions(inBehindBarrierCentralArea, f2r2c0ER, f2r1c2LG, f2r3c2WB), turnKeyMirror);
		deathAltarPuzzle.addStep(new Conditions(inTempleStairSquare, f2r2c0ER, f2r1c2LG, f2r3c2WB), goDownToCentre);
		deathAltarPuzzle.addStep(new Conditions(inTempleStairSquare, f2r2c0ER, f2r1c2LG, f2r3c2WB), goDownToCentre);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2, f2r2c0ER, f2r1c2LG, f2r3c2WB), goDownToMiddleFromNorthPuzzle6);
		deathAltarPuzzle.addStep(new Conditions(inNorthF2, f2r4c0SR, f2r1c2LG, f2r3c2WB), puzzle6Pillar17);
		deathAltarPuzzle.addStep(new Conditions(inNorthF2, f2r4c3WY, f2r1c2LG, f2r3c2WB), puzzle6Pillar16);
		deathAltarPuzzle.addStep(new Conditions(inNorthF2, f2r4c3WY, f2r1c2LG, f2r3c3W), puzzle6Pillar15);
		deathAltarPuzzle.addStep(new Conditions(inNorthF2, f2r4c3WY, f2r1c2LG, f2r1c3N), puzzle6Pillar14);
		deathAltarPuzzle.addStep(new Conditions(inTempleStairSquare, f2r4c3WY, f2r1c2LG, f2r1c3N), goUpFromMiddleToNorthPuzzle6);
		deathAltarPuzzle.addStep(new Conditions(inSouthF2, f2r4c3WY, f2r1c2LG, f2r1c3N), goDownToMiddleFromSouthPuzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2, f2r4c3WY, f2r1c2LG, f2r1c4L), puzzle6Pillar13);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2, f2r4c3WY, f2r1c2LG, f0r1c4U), puzzle6Pillar12);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2NorthRoom, f2r4c3WY, f0r1c2U, f0r1c4U), goDownNorthLadderToF1Puzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2, f2r4c3WY, f0r1c2U, f0r1c4U), puzzle6Pillar11);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, f2r4c3WY, f0r1c2U, f0r1c4U), goUpToFloor2Puzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF2, f1r4c3UY, f0r1c2U, f0r1c4U), puzzle6Pillar10);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, f1r4c3UY, f0r1c2U, f0r1c4U), goUpNorthLadderToF2Puzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, f0r4c3U, f0r1c2U, f0r1c4U), puzzle6Pillar9);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r4c3U, f0r1c2U, f0r1c4U), goUpToF1Puzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r1c3F, f0r4c3U, f0r1c2U), puzzle6Pillar8);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r1c3F, f0r4c3U), puzzle6Pillar7);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r3c3F, f0r4c3U), puzzle6Pillar6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r3c3F), puzzle6Pillar5);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f0r3c4W), puzzle6Pillar4);
		deathAltarPuzzle.addStep(new Conditions(inTempleF0, f1r3c4D), puzzle6Pillar3);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, f1r3c4D), goDownFromF1Puzzle6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, f1r3c3U), pullDispenser6);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, startColumnN), puzzle6Pillar2);
		deathAltarPuzzle.addStep(new Conditions(inTempleF1, dispenserEmpty), puzzle6Pillar1);
		deathAltarPuzzle.addStep(inTempleF1, pullDispenser6);
		deathAltarPuzzle.addStep(inTempleF2, genericGoDownToFloor1);
		deathAltarPuzzle.addStep(inTempleF0, genericGoUpToFloor1);
		deathAltarPuzzle.addStep(inCave, enterTempleOfLight);
		deathAltarPuzzle.addStep(inMournerBasement, goBackIntoMournerCave);
		deathAltarPuzzle.addStep(inMournerHQ, goBackIntoMournerBasement);

		ConditionalStep addCrystal = new ConditionalStep(this, getDeathTalisman);
		addCrystal.addStep(new Conditions(inNorthF2, chargedCrystal), useCrystalOnCrystal);
		addCrystal.addStep(new Conditions(inBehindBarrierCentralArea, chargedCrystal), goUpFromCentre);
		addCrystal.addStep(new Conditions(inCentralArea, chargedCrystal, redAtDoor), goUpFromCentre);
		addCrystal.addStep(new Conditions(inCentralArea, chargedCrystal, redAtAltar), turnPillarFromTemple);
		addCrystal.addStep(new Conditions(inDeathAltarArea, chargedCrystal, redAtAltar), turnPillarFromTemple);
		addCrystal.addStep(new Conditions(inTempleStairSquare, chargedCrystal), goUpToNorthToCharge);
		addCrystal.addStep(new Conditions(inSouthF2, chargedCrystal), goDownToMiddleFromSouthCrystal);
		addCrystal.addStep(new Conditions(inTempleF1, chargedCrystal), enterFloor2Crystal);
		addCrystal.addStep(new Conditions(inCaveOrF0, chargedCrystal), enterFloor1Crystal);
		addCrystal.addStep(new Conditions(inMournerBasement, chargedCrystal), enterMournerCaveCrystal);
		addCrystal.addStep(new Conditions(inMournerHQ, chargedCrystal), enterMournerBasementCrystal);
		addCrystal.addStep(new Conditions(inDeathAltarArea, chargedCrystal), enterMournerHQCrystal);
		addCrystal.addStep(new Conditions(inDeathAltar, chargedCrystal), leaveDeathAltar);
		addCrystal.addStep(inDeathAltar, useCrystalOnAltar);
		addCrystal.addStep(new Conditions(deathTalisman, inDeathAltarArea), enterDeathAltar);
		addCrystal.addStep(new Conditions(deathTalisman, redAtAltar, inCentralArea), enterDeathAltar);
		addCrystal.addStep(new Conditions(deathTalisman, redAtDoor, inCentralArea), turnKeyMirrorCharging);
		addCrystal.addStep(new Conditions(inDeathAltarArea, redAtAltar), getDeathTalismanInCentre);
		addCrystal.addStep(new Conditions(inCentralArea), getDeathTalismanInCentre);
		addCrystal.addStep(new Conditions(inDeathAltarArea), getDeathTalismanInCentreDoorCorrect);
		addCrystal.addStep(new Conditions(redAtDoor, inBehindBarrierCentralArea), turnKeyMirrorCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inTempleStairSquare), goDownToCentreCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inTempleF2), goDownToMiddleFromSouthCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inTempleF1), goUpToF2ForCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inCaveOrF0), goUpToF1ForCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inMournerBasement), enterMournerCaveCharging);
		addCrystal.addStep(new Conditions(redAtDoor, inMournerHQ), enterMournerBasementCharging);
		addCrystal.addStep(new Conditions(redAtDoor), enterMournerHQCharging);
		addCrystal.addStep(new Conditions(inIbanRoom), leavePassCentre);
		addCrystal.addStep(new Conditions(inPassF0), enterAltarFromBehind);
		addCrystal.addStep(new Conditions(inPassF1), enterSouthPass);
		addCrystal.addStep(new Conditions(inWellEntrance), enterWell);
		addCrystal.addStep(new Conditions(redAtAltar), enterUndergroundPass);

		ConditionalStep doAllPuzzles = new ConditionalStep(this, talkToArianwynAfterGivingCrystal);
		doAllPuzzles.addStep(new Conditions(enteredDeathArea), addCrystal);
		doAllPuzzles.addStep(new Conditions(solvedPuzzle5), deathAltarPuzzle);
		doAllPuzzles.addStep(new Conditions(solvedPuzzle3, solvedPuzzle4, placedBlueCrystalInJumpRoom), puzzle5Part2);
		doAllPuzzles.addStep(new Conditions(solvedPuzzle3, solvedPuzzle4), puzzle5PlaceBlue);
		doAllPuzzles.addStep(solvedPuzzle3, puzzle4);
		doAllPuzzles.addStep(solvedPuzzle2, puzzle3);
		doAllPuzzles.addStep(solvedPuzzle1, puzzle2);
		doAllPuzzles.addStep(knowToUseCrystal, puzzle1);

		steps.put(40, doAllPuzzles);

		steps.put(50, returnToArianwyn);

		return steps;
	}

	@Override
	protected void setupRequirements()
	{
		deathTalisman = new ItemRequirement("Access to the Death Altar", ItemCollections.DEATH_ALTAR).isNotConsumed();
		deathTalisman.setTooltip("Death Talisman/Tiara, Catalytic Talisman/Tiara or RC-skill cape");
		deathTalisman.appendToTooltip("or bring the dwarf the 50 items asked later");
		deathTalismanHeader = new ItemRequirement("Access to the Death Altar or 50 items asked of you by a dwarf", ItemCollections.DEATH_ALTAR).isNotConsumed();
		deathTalisman.setTooltip("Death Talisman/Tiara, Catalytic Talisman/Tiara or RC-skill cape");

		mournerBoots = new ItemRequirement("Mourner boots", ItemID.MOURNING_MOURNER_BOOTS, 1, true).isNotConsumed().highlighted();
		gasMask = new ItemRequirement("Gas mask", ItemID.GASMASK, 1, true).isNotConsumed().highlighted();
		mournerGloves = new ItemRequirement("Mourner gloves", ItemID.MOURNING_MOURNER_GLOVES, 1, true).isNotConsumed().highlighted();
		mournerCloak = new ItemRequirement("Mourner cloak", ItemID.MOURNING_MOURNER_CLOAK, 1, true).isNotConsumed().highlighted();
		mournerTop = new ItemRequirement("Mourner top", ItemID.MOURNING_MOURNER_TOP, 1, true).isNotConsumed().highlighted();
		mournerTrousers = new ItemRequirement("Mourner trousers", ItemID.MOURNING_MOURNER_LEGS, 1, true).isNotConsumed().highlighted();
		mournersOutfit = new ItemRequirements("Full mourners' outfit", gasMask, mournerTop, mournerTrousers, mournerCloak, mournerBoots, mournerGloves).isNotConsumed();

		// Teleports
		lletyaTeleport = new ItemRequirement("Lletya teleport. Teleport crystal", ItemCollections.TELEPORT_CRYSTAL).isNotConsumed();
		westArdougneTeleport = new ItemRequirement("West ardougne teleport", ItemID.TELETAB_WESTARDY);
		westArdougneTeleport.addAlternates(ItemID.POH_TABLET_ARDOUGNETELEPORT);

		rope = new ItemRequirement("Rope", ItemID.ROPE);
		ropeHighlight = new ItemRequirement("Rope", ItemID.ROPE);
		ropeHighlight.setHighlightInInventory(true);

		chisel = new ItemRequirement("Chisel", ItemID.CHISEL).isNotConsumed();
		staminaPotions = new ItemRequirement("Stamina potions", ItemCollections.STAMINA_POTIONS);
		prayerPotions = new ItemRequirement("Prayer potions for Protect from Melee",
			ItemCollections.PRAYER_POTIONS, -1);
		food = new ItemRequirement("Food", ItemCollections.GOOD_EATING_FOOD, -1);

		newKey = new KeyringRequirement("New Key", configManager, KeyringCollection.NEW_KEY);
		newKey.setTooltip("You can get another from Essyllt's desk");

		edernsJournal = new ItemRequirement("Edern's journal", ItemID.MOURNING_EDERNS_JOURNAL);
		blackenedCrystal = new ItemRequirement("Blackened crystal", ItemID.MOURNING_CRYSTAL_SAMPLE);

		newlyMadeCrystal = new ItemRequirement("Newly made crystal", ItemID.MOURNING_CRYSTAL_NEW_SAMPLE);
		newlyMadeCrystal.setTooltip("You can get another from Arianwyn in Llyeta");

		newlyMadeCrystalHighlight = new ItemRequirement("Newly made crystal", ItemID.MOURNING_CRYSTAL_NEW_SAMPLE);
		newlyMadeCrystalHighlight.setTooltip("You can get another from Arianwyn in Llyeta");
		newlyMadeCrystalHighlight.setHighlightInInventory(true);

		newlyIfOneTrip = new ItemRequirement("Newly made crystal (if already have death talisman)", ItemID.MOURNING_CRYSTAL_NEW_SAMPLE);

		mirror = new ItemRequirement("Hand mirror", ItemID.MOURNING_MIRROR);
		mirror.setTooltip("If you've misplaced a mirror, you can pull the crystal dispenser in the east of the middle " +
			"floor to reset the puzzle");
		mirror.setHighlightInInventory(true);

		yellowCrystal = new ItemRequirement("Yellow crystal", ItemID.MOURNING_CRYSTAL_YELLOW);
		yellowCrystal.setTooltip("Check the crystal dispenser if you've lost this");
		yellowCrystal.setHighlightInInventory(true);
		cyanCrystal = new ItemRequirement("Cyan crystal", ItemID.MOURNING_CRYSTAL_CYAN);
		cyanCrystal.setTooltip("Check the crystal dispenser if you've lost this");
		cyanCrystal.setHighlightInInventory(true);
		blueCrystal = new ItemRequirement("Blue crystal", ItemID.MOURNING_CRYSTAL_BLUE);
		blueCrystal.setTooltip("Check the crystal dispenser if you've lost this");
		blueCrystal.setHighlightInInventory(true);
		fracturedCrystal = new ItemRequirement("Fractured crystal", ItemID.MOURNING_FRACTURED_CRYSTAL_1);
		fracturedCrystal.setHighlightInInventory(true);
		fracturedCrystal.setTooltip("Check the crystal dispenser if you've lost this");
		fracturedCrystal2 = new ItemRequirement("Fractured crystal", ItemID.MOURNING_FRACTURED_CRYSTAL_2);
		fracturedCrystal2.setHighlightInInventory(true);
		fracturedCrystal2.setTooltip("Check the crystal dispenser if you've lost this");
		chargedCrystal = new ItemRequirement("Newly made crystal", ItemID.MOURNING_CRYSTAL_NEW_POWERED);
		chargedCrystalHighlight = new ItemRequirement("Newly made crystal", ItemID.MOURNING_CRYSTAL_NEW_POWERED);
		chargedCrystalHighlight.setHighlightInInventory(true);
	}

	public void setupConditions()
	{
		inMournerBasement = new ZoneRequirement(mournerBasement);
		inMournerHQ = new ZoneRequirement(mournerHQ, mournerHQ2);
		inCave = new ZoneRequirement(cave);

		inTempleF0 = new ZoneRequirement(templeF0);
		inTempleF1 = new ZoneRequirement(templeF1);
		inTempleF2 = new ZoneRequirement(northTempleF2, southTempleF2);
		inTempleF2NorthRoom = new ZoneRequirement(northRoomF2);

		inCaveOrF0 = new ZoneRequirement(cave, templeF0);
		inTempleStairSquare = new ZoneRequirement(templeStairSquare);

		inBlueRoom = new ZoneRequirement(blueRoom);
		inYellowRoom = new ZoneRequirement(yellowRoom1, yellowRoom2);
		inCyanRoom = new ZoneRequirement(cyanRoom1, cyanRoom2);

		inNorthF2 = new ZoneRequirement(northTempleF2);
		inSouthF2 = new ZoneRequirement(southTempleF2);

		inCentralArea = new ZoneRequirement(centralArea);
		inDeathAltarArea = new ZoneRequirement(deathAltarArea);

		inPassF0 = new ZoneRequirement(passF0);
		inPassF1 = new ZoneRequirement(passF1);

		inIbanRoom = new ZoneRequirement(ibanRoom);
		inWellEntrance = new ZoneRequirement(wellEntrance);

		inBehindBarrierCentralArea = new ZoneRequirement(centralAreaBehindBarrier);

		inDeathAltar = new ZoneRequirement(deathAltar);

		dispenserEmpty = new VarbitRequirement(1106, 0);

		// 1108 yellow crystal in dispenser
		// 1107, blue crystal in dispenser

		usedRope = new VarbitRequirement(1328, 1);

		knowToUseCrystal = new VarbitRequirement(1104, 1);

		solvedPuzzle1 = new VarbitRequirement(1113, 1);
		solvedPuzzle2 = new VarbitRequirement(1114, 1);
		solvedPuzzle3 = new VarbitRequirement(1116, 1);
		solvedPuzzle4 = new VarbitRequirement(1115, 1);
		solvedPuzzle5 = new VarbitRequirement(1117, 1);

		enteredDeathArea = new VarbitRequirement(1330, 1);

		receivedList = new VarbitRequirement(1327, 1);

		final int RED = 1;
		final int YELLOW = 2;
		final int GREEN = 3;
		final int CYAN = 4;
		final int BLUE = 5;
		final int WHITE = 7;

		// Floor 0
		f0r3c4W = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_6_7, WHITE);
		f0r3c3F = new Conditions(new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_6_11, WHITE), new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_3_6 , WHITE));
		f0r4c3U = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_3_UP, WHITE);
		f0r1c3F = new Conditions(new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_11_12, WHITE), new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_10_11, WHITE));
		f0r1c2U = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_10_UP, GREEN);
		f0r1c4U = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_12_UP, WHITE);
		f0r4c0SB = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_1_SOUTH, BLUE);
		f0r1c2SG = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_10_14, GREEN);
		f0r0c2EG = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_14_EAST, GREEN);
		f0r0c4NB = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_16_NORTH, BLUE);

		// Floor 1
		f1r0c3EY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_15_EAST, YELLOW);

		// TODO: Add other directions from this pillar
		// 1226 = up?
		// 1192 = down?
		// West: 1210?
		f1r0c3 = new Conditions(LogicType.OR,
			new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_15_EAST, 1, Operation.GREATER_EQUAL), // East
			new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_15_UP, 1, Operation.GREATER_EQUAL), // Up
			new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_15_UP, 1, Operation.GREATER_EQUAL), // Down
			new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_15_WEST, 1, Operation.GREATER_EQUAL) // West
		);

		// 1269 0->16???

		f1r1c3SY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_11_15, YELLOW);
		f1r3c2NC = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_2_5, CYAN);
		f1r3c2NY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_2_5, YELLOW);
		f1r3c3WC = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_5_6, CYAN);
		f1r3c3WY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_5_6, YELLOW);
		f1r3c3S = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_6_11, WHITE);
		f1r3c3U = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_6_UP, WHITE);
		f1r3c4W = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_6_7, WHITE);
		f1r3c4D = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_7_UP, WHITE);
		f1r4c2EC = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_2_3, CYAN);
		f1r4c2EY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_2_3, YELLOW);
		f1r4c3EG = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_3_EAST, GREEN);
		f1r4c3UC = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_3_UP, CYAN);
		f1r4c3UY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_3_UP, YELLOW);
		startColumnN = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_7_9, WHITE);
		redAtDoor = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_B_EAST, RED);
		redAtAltar = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_B_WEST, RED);

		// Floor 2
		f2r0c0DR = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_13_UP, RED);
		f2r2c0SNotRed = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_8_13, RED, Operation.NOT_EQUAL);
		f2r3c3S = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_6_11, WHITE);
		f2r4c3WC = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_2_3, CYAN);
		f2r4c3WY = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_2_3, YELLOW);
		f2r4c0DB = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_1_UP, BLUE);
		f2r4c0SR = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_1_8, RED);
		f2r1c3F = new Conditions(new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_11_12, WHITE), new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_10_11, 7),
				new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_11_15, WHITE));
		f2r1c2D = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_2_10_UP, WHITE);
		f2r0c3E = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_15_16, WHITE);
		f2r1c2LG = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_10_WEST, GREEN);
		f2r1c4L = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_11_12, WHITE);
		f2r1c3N = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_6_11, WHITE);
		f2r3c3W = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_5_6, WHITE);
		f2r3c2WB = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_5_WEST, BLUE);
		f2r2c0ER = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_3_8_EAST, RED);
		f2r0c4D = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_16_UP, BLUE);
		blueCrystalNotPlaced = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_16_UP, WHITE);
		placedBlueCrystalInJumpRoom = new Conditions(true, LogicType.OR, f2r0c4D, new Conditions(LogicType.AND, inBlueRoom, new WidgetTextRequirement(InterfaceID.Objectbox.TEXT, "You place the blue crystal in the pillar.")));
		cyanDoorOpen = new VarbitRequirement(VarbitID.MOURNING_LIGHT_TEMPLE_1_13_EAST, RED);
	}

	@Override
	protected void setupZones()
	{
		mournerHQ = new Zone(new WorldPoint(2547, 3321, 0), new WorldPoint(2555, 3327, 0));
		mournerHQ2 = new Zone(new WorldPoint(2542, 3324, 0), new WorldPoint(2546, 3327, 0));
		mournerBasement = new Zone(new WorldPoint(2034, 4628, 0), new WorldPoint(2045, 4651, 0));
		cave = new Zone(new WorldPoint(1919, 4590, 0), new WorldPoint(2033, 4687, 0));
		templeF0 = new Zone(new WorldPoint(1853, 4608, 0), new WorldPoint(1918, 4673, 0));
		templeF1 = new Zone(new WorldPoint(1853, 4608, 1), new WorldPoint(1918, 4673, 1));
		southTempleF2 = new Zone(new WorldPoint(1853, 4608, 2), new WorldPoint(1918, 4634, 2));
		northTempleF2 = new Zone(new WorldPoint(1853, 4635, 2), new WorldPoint(1918, 4673, 2));
		northRoomF2 = new Zone(new WorldPoint(1891, 4659, 2), new WorldPoint(1918, 4667, 2));
		templeStairSquare = new Zone(new WorldPoint(1890, 4637, 1), new WorldPoint(1892, 4641, 1));
		blueRoom = new Zone(new WorldPoint(1911, 4611, 1), new WorldPoint(1917, 4615, 1));
		yellowRoom1 = new Zone(new WorldPoint(1858, 4647, 0), new WorldPoint(1862, 4668, 0));
		yellowRoom2 = new Zone(new WorldPoint(1863, 4652, 0), new WorldPoint(1880, 4661, 0));
		cyanRoom1 = new Zone(new WorldPoint(1858, 4611, 0), new WorldPoint(1862, 4627, 0));
		cyanRoom2 = new Zone(new WorldPoint(1863, 4618, 0), new WorldPoint(1881, 4623, 0));
		centralAreaBehindBarrier = new Zone(new WorldPoint(1886, 4638, 0), new WorldPoint(1886, 4640, 0));
		centralArea = new Zone(new WorldPoint(1866, 4625, 0), new WorldPoint(1885, 4655, 0));
		deathAltarArea = new Zone(new WorldPoint(1857, 4635, 0), new WorldPoint(1865, 4643, 0));
		passF1 = new Zone(new WorldPoint(2105, 4540, 1), new WorldPoint(2187, 4750, 1));
		passF0 = new Zone(new WorldPoint(2296, 9781, 0), new WorldPoint(2400, 9921, 0));
		deathAltar = new Zone(new WorldPoint(2183, 4812, 0), new WorldPoint(2237, 4869, 0));

		wellEntrance = new Zone(new WorldPoint(2311, 9608, 0), new WorldPoint(2354, 9637, 0));
		ibanRoom = new Zone(new WorldPoint(1999, 4704, 1), new WorldPoint(2015, 4717, 1));
	}

	public void setupSteps()
	{
		talkToArianwyn = new NpcStep(this, NpcID.MOURNING_ARIANWYN_VIS, new WorldPoint(2354, 3170, 0), "Talk to Arianwyn in Lletya.");
		talkToArianwyn.addDialogStep("Yes.");
		talkToArianwyn.addTeleport(lletyaTeleport);

		enterMournerHQ = new ObjectStep(this, ObjectID.MOURNERSTEWDOOR, new WorldPoint(2551, 3320, 0),
			"Talk to Essyllt in the Mourner HQ basement.", chisel, gasMask, mournerTop, mournerTrousers, mournerCloak, mournerGloves, mournerBoots);
		enterMournerHQ.addTeleport(westArdougneTeleport);
		enterMournerBasement = new ObjectStep(this, ObjectID.MOURNING_HIDEOUT_TRAP_DOOR, new WorldPoint(2542, 3327, 0), "Enter the Mourner HQ basement.");

		talkToEssyllt = new NpcStep(this, NpcID.MOURNER_HIDEOUT_HEAD_MOURNER_VIS, new WorldPoint(2043, 4631, 0), "Talk to Essyllt.");
		talkToEssyllt.addSubSteps(enterMournerHQ, enterMournerHQ);

		enterCave = new ObjectStep(this, ObjectID.MOURNER_HIDEOUT_DOOR4, new WorldPoint(2034, 4636, 0), "Enter the Mourner Caves.", newKey);

		searchCorpse = new ObjectStep(this, ObjectID.MOURNING_DEAD_GUARD4, new WorldPoint(1925, 4642, 0), "Follow the caves to the west until you reach the Temple of Light. Search the guard corpse nearby.");

		goUpStairsTemple = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Follow the caves to the west to the Temple of Light. Go up the stairs in it.");

		goUpSouthLadder = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase.");

		goUpFromMiddleToNorth = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4642, 1), "Go up the stairs to the north.");

		goToMiddleFromSouth = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down the staircase north of you.");

		useChisel = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_OBSIDIAN_CRYSTAL, new WorldPoint(1909, 4639, 2),
			"Use a chisel on the dark crystal to the east.", chisel.highlighted());
		useChisel.addIcon(ItemID.CHISEL);

		goUpFromMiddleToSouth = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4636, 1), "Go up the stairs to the south.");

		goDownToEnd = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1888, 4639, 1), "Go down the stairs to the west.");

		useRope = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_WAY_DOWN, new WorldPoint(1876, 4620, 1), "Use a rope on the rocks in the small room in the south of the first floor.", ropeHighlight);
		useRope.addIcon(ItemID.ROPE);
		bringCrystalToArianwyn = new NpcStep(this, NpcID.MOURNING_ARIANWYN_VIS, new WorldPoint(2354, 3170, 0), "Bring the crystal to Arianwyn in Lletya.", blackenedCrystal);
		bringCrystalToArianwyn.addTeleport(lletyaTeleport);
		talkToElunedAfterGivingCrystal = new NpcStep(this, NpcID.ROVING_FEMALE_WOODELF_TEMP_1, new WorldPoint(2354, 3169, 0), "Talk to Eluned in Lletya next to Arianwyn.");
		talkToArianwynAfterGivingCrystal = new NpcStep(this, NpcID.MOURNING_ARIANWYN_VIS, new WorldPoint(2354, 3170, 0), "Talk to Arianwyn in Lletya.");
		talkToArianwynAfterGivingCrystal.setLockingCondition(knowToUseCrystal);

		goBackIntoMournerHQ = new ObjectStep(this, ObjectID.MOURNERSTEWDOOR, new WorldPoint(2551, 3320, 0),
			"Return to the Temple of Light.", rope, newKey, gasMask, mournerTop, mournerTrousers, mournerCloak, mournerGloves, mournerBoots, deathTalisman, newlyIfOneTrip);
		goBackIntoMournerHQ.addTeleport(westArdougneTeleport);
		goBackIntoMournerBasement = new ObjectStep(this, ObjectID.MOURNING_HIDEOUT_TRAP_DOOR, new WorldPoint(2542, 3327, 0), "Enter the Mourner HQ basement.");

		goBackIntoMournerCave = new ObjectStep(this, ObjectID.MOURNER_HIDEOUT_DOOR4, new WorldPoint(2034, 4636, 0), "Enter the Mourner Caves.", newKey);

		enterTempleOfLight = new DetailedQuestStep(this, new WorldPoint(1907, 4640, 0), "If you are an ironman, you can earn the death talisman later. Return to the Temple of Light. Make sure you're prepared for shadows to be continuously attacking you.");
		enterTempleOfLight.addSubSteps(goBackIntoMournerHQ, goBackIntoMournerBasement, goBackIntoMournerCave);

		goUpStairsTempleC1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Go up the stairs to the first floor.");

		pullDispenser1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room.");
		pullDispenser1.addDialogStep("Pull the lever.");
		pullDispenser1.addDialogStep("Pull it.");
		pullDispenser1.addDialogStep("Take everything.");

		puzzle1Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle1Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle1Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it west.", mirror);
		puzzle1Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		puzzle1Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Put a mirror in the pillar to the west and point it south.", mirror);
		puzzle1Pillar3.addIcon(ItemID.MOURNING_MIRROR);
		puzzle1Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_11, new WorldPoint(1898, 4628, 1), "Put a yellow crystal in the pillar to the south.", yellowCrystal);
		puzzle1Pillar4.addIcon(ItemID.MOURNING_CRYSTAL_YELLOW);
		puzzle1Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_15, new WorldPoint(1898, 4613, 1), "Put a mirror in the pillar to the south and point it east.", mirror);
		puzzle1Pillar5.addIcon(ItemID.MOURNING_MIRROR);
		climbWallSupport = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_AGILITY_HANGING, new WorldPoint(1902, 4612, 1), "Attempt to climb the wall support to the south west. " +
			"You may fail this multiple times. Agility boosts help your chances.");
		searchBlueChest = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_2_CLOSED, new WorldPoint(1917, 4613, 1), "Search the chest in the blue light room.");
		searchBlueChest.addAlternateObjects(ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_2_OPEN);

		pullDispenser2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room to reset the puzzle.");
		pullDispenser2.addDialogStep("Pull it.");
		pullDispenser2.addDialogStep("Take everything.");

		puzzle2Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle2Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle2Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it west.", mirror);
		puzzle2Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		puzzle2Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Put a cyan crystal in the pillar to the west.", cyanCrystal);
		puzzle2Pillar3.addIcon(ItemID.MOURNING_CRYSTAL_CYAN);
		puzzle2Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_5, new WorldPoint(1887, 4650, 1), "Put a mirror in the pillar to the west and point it north.", mirror);
		puzzle2Pillar4.addIcon(ItemID.MOURNING_MIRROR);
		puzzle2Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_2, new WorldPoint(1887, 4665, 1), "Put a mirror in the pillar to the north and point it east.", mirror);
		puzzle2Pillar5.addIcon(ItemID.MOURNING_MIRROR);
		puzzle2Pillar6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_3, new WorldPoint(1898, 4665, 1), "Put a yellow crystal in the pillar to the east.", yellowCrystal);
		puzzle2Pillar6.addIcon(ItemID.MOURNING_CRYSTAL_YELLOW);

		searchMagentaChest = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_3_CLOSED, new WorldPoint(1917, 4665, 1), "Search the chest in the magenta room in the north east corner.");
		searchMagentaChest.addAlternateObjects(ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_3_OPEN);

		pullDispenser3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room to reset the puzzle.");
		pullDispenser3.addDialogStep("Pull it.");
		pullDispenser3.addDialogStep("Take everything.");

		puzzle3Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle3Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle3Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it west.", mirror);
		puzzle3Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		puzzle3Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Put a cyan crystal in the pillar to the west.", cyanCrystal);
		puzzle3Pillar3.addIcon(ItemID.MOURNING_CRYSTAL_CYAN);
		puzzle3Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_5, new WorldPoint(1887, 4650, 1), "Put a mirror in the pillar to the west and point it north.", mirror);
		puzzle3Pillar4.addIcon(ItemID.MOURNING_MIRROR);
		puzzle3Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_2, new WorldPoint(1887, 4665, 1), "Put a mirror in the pillar to the north and point it east.", mirror);
		puzzle3Pillar5.addIcon(ItemID.MOURNING_MIRROR);
		puzzle3Pillar6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_3, new WorldPoint(1898, 4665, 1), "Put a mirror in the pillar to the east and point it up.", mirror);
		puzzle3Pillar6.addIcon(ItemID.MOURNING_MIRROR);
		puzzle3Pillar6RemoveYellow = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_3, new WorldPoint(1898, 4665, 1), "Take the yellow crystal out of the pillar, and replace it with a mirror pointing up.");
		puzzle3Pillar6RemoveYellow.addSubSteps(puzzle3Pillar6);

		goUpLadderNorthForPuzzle3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL, new WorldPoint(1898, 4668, 1), "Go up the north ladder to the second floor.");
		puzzle3Pillar7 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_3, new WorldPoint(1898, 4665, 2), "Put a mirror in the pillar next to you and point it west.", mirror);
		puzzle3Pillar7.addIcon(ItemID.MOURNING_MIRROR);
		goDownFromF2NorthRoomPuzzle3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL_TOP, new WorldPoint(1898, 4668, 2), "Go down the north ladder to the first floor.");
		goUpToFloor2Puzzle3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase to the second floor.");
		puzzle3Pillar8 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_1, new WorldPoint(1860, 4665, 2), "Place a mirror in the far north-west corner's pillar pointing down.", mirror);
		puzzle3Pillar8.addIcon(ItemID.MOURNING_MIRROR);
		goDownFromF2Puzzle3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_TOP, new WorldPoint(1894, 4620, 2), "Go down to the ground floor.");
		goDownFromF1Puzzle3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1903, 4639, 1), "Go down to the ground floor.");
		goDownFromF2Puzzle3.addSubSteps(goDownFromF1Puzzle3);
		enterNorthWestRoomPuzzle3 = new ObjectStep(this, ObjectID.MOURNING_DOOR_1_1_EAST, new WorldPoint(1863, 4665, 0), "Enter the room in the far north west corner.");
		puzzle3Pillar9 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_1, new WorldPoint(1860, 4665, 0), "Turn the mirror in the pillar in the room to point the light south.");
		searchYellowChest = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_5_CLOSED, new WorldPoint(1880, 4659, 0), "Search the chest in the room south of you.");
		searchYellowChest.addAlternateObjects(ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_5_OPEN);

		yellowRoomRotateToLeave = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_1, new WorldPoint(1860, 4665, 0), "Turn the mirror in the pillar in the room to point the light east to leave.");
		goUpToFirstFloorPuzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Go up the stairs to the first floor.");

		pullDispenser4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room to reset the puzzle.");
		pullDispenser4.addDialogStep("Pull it.");
		pullDispenser4.addDialogStep("Take everything.");

		puzzle4Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle4Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle4Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it west.", mirror);
		puzzle4Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		puzzle4Pillar3RemoveCyan = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Take the cyan crystal out of the pillar north of the stairs, and replace it with a yellow crystal.");
		puzzle4Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Put a yellow crystal in the pillar to north west of the dispenser.", yellowCrystal);
		puzzle4Pillar3.addIcon(ItemID.MOURNING_CRYSTAL_YELLOW);
		puzzle4Pillar3RemoveCyan.addSubSteps(puzzle4Pillar3);
		puzzle4Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_5, new WorldPoint(1887, 4650, 1), "Put a mirror in the pillar to the west and point it north.", mirror);
		puzzle4Pillar4.addIcon(ItemID.MOURNING_MIRROR);
		puzzle4Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_2, new WorldPoint(1887, 4665, 1), "Put a mirror in the pillar to the north and point it east.", mirror);
		puzzle4Pillar5.addIcon(ItemID.MOURNING_MIRROR);
		puzzle4Pillar6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_3, new WorldPoint(1898, 4665, 1), "Put a mirror in the pillar to the east and point it up.", mirror);
		puzzle4Pillar6.addIcon(ItemID.MOURNING_MIRROR);
		goUpLadderNorthForPuzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL, new WorldPoint(1898, 4668, 1), "Go up the north ladder to the second floor.");
		puzzle4Pillar7 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_3, new WorldPoint(1898, 4665, 2), "Put a mirror in the pillar next to you and point it west.", mirror);
		puzzle4Pillar7.addIcon(ItemID.MOURNING_MIRROR);
		goDownFromF2NorthRoomPuzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL_TOP, new WorldPoint(1898, 4668, 2), "Go down the north ladder to the first floor.");
		goUpToFloor2Puzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase to the second floor.");
		puzzle4Pillar8 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_1, new WorldPoint(1860, 4665, 2), "Place a mirror in the far north-west corner's pillar pointing south.", mirror);
		puzzle4Pillar8.addIcon(ItemID.MOURNING_MIRROR);
		puzzle4Pillar9 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_13, new WorldPoint(1860, 4613, 2), "Go to the far south-west corner and place a mirror pointing down.", mirror);
		puzzle4Pillar9.addIcon(ItemID.MOURNING_MIRROR);
		removeMirrorPuzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_8, new WorldPoint(1860, 4639, 2), "Remove the item from the pillar in the west of the second floor.");
		goDownFromF2Puzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_TOP, new WorldPoint(1894, 4620, 2), "Go down to the first floor.");
		goDownRope = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_WAY_DOWN, new WorldPoint(1876, 4620, 1), "Go down the rope you tied in the south room of the first floor.");
		searchCyanChest = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_4_CLOSED, new WorldPoint(1858, 4613, 0), "Search the chest in the south west room.");
		searchCyanChest.addAlternateObjects(ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_4_OPEN);

		goToGroundFloorPuzzle4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1903, 4639, 1), "Go down to the ground floor.");
		enterCyanDoor = new ObjectStep(this, ObjectID.MOURNING_DOOR_1_13_EAST, new WorldPoint(1863, 4613, 0), "Enter the south west cyan room and search the chest.");

		climbUpRope = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_WAY_ROPEMULTI, new WorldPoint(1877, 4620, 0), "Return to the first floor and reset the puzzle.");

		pullDispenser5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room to reset the puzzle, and take all the items.");
		pullDispenser5.addDialogStep("Pull it.");
		pullDispenser5.addDialogStep("Take everything.");

		puzzle5Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle5Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it west.", mirror);
		puzzle5Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Put a mirror in the pillar to the west and point it south.", mirror);
		puzzle5Pillar3.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_11, new WorldPoint(1898, 4628, 1), "Put a yellow crystal in the pillar to the south.", yellowCrystal);
		puzzle5Pillar4.addIcon(ItemID.MOURNING_CRYSTAL_YELLOW);
		puzzle5Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_15, new WorldPoint(1898, 4613, 1), "Put a mirror in the pillar to the south and point it east.", mirror);
		puzzle5Pillar5.addIcon(ItemID.MOURNING_MIRROR);
		climbWallSupportPuzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_AGILITY_HANGING, new WorldPoint(1902, 4612, 1), "Make sure you have the blue crystal, and attempt to climb the wall support to the south west. You may fail this multiple times.", blueCrystal);
		puzzle5Pillar6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_16, new WorldPoint(1915, 4613, 1), "Put a blue crystal in the pillar in the blue room.", blueCrystal);
		puzzle5Pillar6.addIcon(ItemID.MOURNING_CRYSTAL_BLUE);

		puzzle5Pillar5RemoveMirror = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_15, new WorldPoint(1898, 4613, 1),
			"REMOVE the mirror from the pillar in the south of the first floor.");
		puzzle5Pillar5RemoveMirror.addIcon(ItemID.BARBASSAULT_PLAYERICON_HEALER);
		puzzle5Pillar3RotateUp = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_6, new WorldPoint(1898, 4650, 1), "Have " +
			"the mirror in the pillar north west of the dispenser point up.", mirror);
		puzzle5Pillar3RotateUp.addIcon(ItemID.MOURNING_MIRROR);
		goUpToFloor2Puzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase to the second floor.");
		goDownToMiddleFromSouthPuzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down the staircase north of you.");
		goUpFromMiddleToNorthPuzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4642, 1), "Go up the stairs to the north.");

		puzzle5Pillar7 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_6, new WorldPoint(1898, 4650, 2), "Place a mirror in the pillar north east of you and face it south.", mirror);
		puzzle5Pillar7.addIcon(ItemID.MOURNING_MIRROR);
		goDownToMiddleFromNorthPuzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4642, 2), "Go back to the south side of floor 2.");
		goUpFromMiddleToSouthPuzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4636, 1), "Go back to the south side of floor 2.");
		goDownToMiddleFromNorthPuzzle5.addSubSteps(goUpFromMiddleToSouthPuzzle5);
		puzzle5Pillar8 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_11, new WorldPoint(1898, 4628, 2), "Place the fractured crystal in the pillar a bit east.", fracturedCrystal);
		puzzle5Pillar8.addIcon(ItemID.MOURNING_FRACTURED_CRYSTAL_1);
		puzzle5Pillar9 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_10, new WorldPoint(1887, 4628, 2), "Place a mirror in the pillar to the west and point it down.", mirror);
		puzzle5Pillar9.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar10 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_15, new WorldPoint(1898, 4613, 2), "Place a mirror in the pillar to the south east and point it east.", mirror);
		puzzle5Pillar10.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar11 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_16, new WorldPoint(1915, 4613, 2), "Place a mirror in the pillar to the east and point it down.", mirror);
		puzzle5Pillar11.addIcon(ItemID.MOURNING_MIRROR);
		goDownFromF2Puzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_TOP, new WorldPoint(1894, 4620, 2), "Go down to the first floor.");

		goDownFromF1Puzzle5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1903, 4639, 1), "Go down to the ground floor.");

		resetPuzzle = new DetailedQuestStep(this, "You haven't placed a blue crystal in the blue room. Place one there to continue.");
		puzzle5Pillar12 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_10, new WorldPoint(1887, 4628, 0), "Add a mirror to the pillar where green light is coming down, and point it south.", mirror);
		puzzle5Pillar12.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar13 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_14, new WorldPoint(1887, 4613, 0), "Add a mirror to the pillar to the south, and point it east.", mirror);
		puzzle5Pillar13.addIcon(ItemID.MOURNING_MIRROR);
		puzzle5Pillar14 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_16, new WorldPoint(1915, 4613, 0), "Enter the south east room, and add a mirror pointing to the north.", mirror);
		puzzle5Pillar14.addIcon(ItemID.MOURNING_MIRROR);

		searchMagentaYellowChest = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_6_CLOSED, new WorldPoint(1910, 4622, 0), "Search the chest in the room north of you.");
		searchMagentaYellowChest.addAlternateObjects(ObjectID.MOURNING_TEMPLE_LIGHT_PARTS_6_OPEN);

		pullDispenser6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LIGHT_WALL_LEVER, new WorldPoint(1913, 4639, 1), "Pull the Crystal Dispenser in the east room to reset the puzzle, and take all the items.");
		pullDispenser6.addDialogStep("Pull it.");
		pullDispenser6.addDialogStep("Take everything.");

		puzzle6Pillar1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_9, new WorldPoint(1909, 4639, 1), "Put a mirror in the pillar next to the dispenser and have the light point north.", mirror);
		puzzle6Pillar1.addIcon(ItemID.MOURNING_MIRROR);
		puzzle6Pillar2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_7, new WorldPoint(1909, 4650, 1), "Put a mirror in the pillar to the north and point it down.", mirror);
		puzzle6Pillar2.addIcon(ItemID.MOURNING_MIRROR);
		goDownFromF1Puzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1903, 4639, 1), "Go down to the ground floor.");

		puzzle6Pillar3 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_7, new WorldPoint(1909, 4650, 0), "Put a mirror in the pillar north of the stairs and have the light point west.", mirror);
		puzzle6Pillar3.addIcon(ItemID.MOURNING_MIRROR);
		puzzle6Pillar4 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_6, new WorldPoint(1898, 4650, 0), "Put the fractured crystal (dark/light side split down middle) in the pillar to the west.", fracturedCrystal2);
		puzzle6Pillar4.addIcon(ItemID.MOURNING_FRACTURED_CRYSTAL_2);

		puzzle6Pillar5 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_3, new WorldPoint(1898, 4665, 0), "Put a mirror in the pillar north and have the light point up.", mirror);
		puzzle6Pillar5.addIcon(ItemID.MOURNING_MIRROR);

		puzzle6Pillar6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_11, new WorldPoint(1898, 4628, 0), "Put the other fractured crystal in the pillar south of the other fractured crystal.", fracturedCrystal);
		puzzle6Pillar6.addIcon(ItemID.MOURNING_FRACTURED_CRYSTAL_1);
		puzzle6Pillar7 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_10, new WorldPoint(1887, 4628, 0), "Add a mirror to the pillar to the west, and point it up.", mirror);
		puzzle6Pillar7.addIcon(ItemID.MOURNING_MIRROR);
		puzzle6Pillar8 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_12, new WorldPoint(1909, 4628, 0), "Put a mirror in the pillar to the east and have the light point up.", mirror);
		puzzle6Pillar8.addIcon(ItemID.MOURNING_MIRROR);

		goUpToF1Puzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Go up the stairs to the first floor.");
		puzzle6Pillar9 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_2_3, new WorldPoint(1898, 4665, 1), "Go to the far north where the ladder going up is, and place the yellow crystal there.", yellowCrystal);
		puzzle6Pillar9.addIcon(ItemID.MOURNING_CRYSTAL_YELLOW);


		goUpNorthLadderToF2Puzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL, new WorldPoint(1898, 4668, 1), "Go up the north ladder to the second floor.");
		puzzle6Pillar10 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_3, new WorldPoint(1898, 4665, 2), "Put a mirror in the pillar next to you and point it west.", mirror);
		puzzle6Pillar10.addIcon(ItemID.MOURNING_MIRROR);
		goDownNorthLadderToF1Puzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_LADDER_WALL_TOP, new WorldPoint(1898, 4668, 2), "Go down the north ladder to the first floor.");
		goUpToFloor2Puzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase to the second floor.");

		puzzle6Pillar11 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_10, new WorldPoint(1887, 4628, 2), "Place a mirror in the pillar to the north of the stairs and point it west.", mirror);
		puzzle6Pillar11.addIcon(ItemID.MOURNING_MIRROR);

		puzzle6Pillar12 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_12, new WorldPoint(1909, 4628, 2), "Place a mirror in the pillar to the far east, and point it west.", mirror);
		puzzle6Pillar12.addIcon(ItemID.MOURNING_MIRROR);

		puzzle6Pillar13 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_11, new WorldPoint(1898, 4628, 2), "Place a mirror to the west and point it north.", mirror);
		puzzle6Pillar13.addIcon(ItemID.MOURNING_MIRROR);

		goDownToMiddleFromSouthPuzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down the staircase north of you.");
		goUpFromMiddleToNorthPuzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4642, 1), "Go up the stairs to the north.");

		puzzle6Pillar14 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_6, new WorldPoint(1898, 4650, 2), "Place a mirror in the pillar north east of the stairs pointing west.", mirror);
		puzzle6Pillar14.addIcon(ItemID.MOURNING_MIRROR);
		puzzle6Pillar15 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_5, new WorldPoint(1887, 4650, 2), "Place the blue crystal in the pillar to the west.", blueCrystal);
		puzzle6Pillar15.addIcon(ItemID.MOURNING_CRYSTAL_BLUE);

		puzzle6Pillar16 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_1, new WorldPoint(1860, 4665, 2), "Place a mirror in the far north-west corner's pillar pointing south.", mirror);
		puzzle6Pillar16.addIcon(ItemID.MOURNING_MIRROR);

		puzzle6Pillar17 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_3_8, new WorldPoint(1860, 4639, 2), "Place a mirror in the south pillar pointing east.", mirror);
		puzzle6Pillar17.addIcon(ItemID.MOURNING_MIRROR);

		goDownToMiddleFromNorthPuzzle6 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down to the central area.");
		goDownToCentre = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1888, 4639, 1), "Go down to the central area.");
		goDownToCentre.addSubSteps(goDownToMiddleFromNorthPuzzle6);
		turnKeyMirror = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0), "Enter the central area, and turn the pillar's mirror west.");

		genericGoUpToFloor1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Go up the stairs to the first floor.");
		genericGoUpToFloor2 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south staircase to the second floor.");
		genericGoDownToFloor0 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1903, 4639, 1), "Go down to the ground floor.");
		genericGoDownToFloor1 = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_TOP, new WorldPoint(1894, 4620, 2), "Go down to the first floor.");

		enterDeathAltarBarrier = new ObjectStep(this, ObjectID.MOURNING_DOOR_1_C, new WorldPoint(1865, 4639, 0), "Enter the barrier to the death altar to the west.");

		if (questHelperPlugin.getPlayerStateManager().getAccountType().isAnyIronman())
		{
			if (client.getRealSkillLevel(Skill.SLAYER) > 85)
			{
				getDeathTalisman = new DetailedQuestStep(this, "You need to get a Death Talisman. Either kill Dark Beasts for one, or bring 50 items requested by Thorgel at the Death Altar to him.");
				getDeathTalismanInCentre = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0),
					"Get a Death Talisman by either getting the dwarf at the altar 50 items, or killing Dark Beasts. " +
						"TURN THE MIDDLE PILLAR TO POINT BACK EAST OR YOU'LL HAVE TO RETURN VIA THE UNDERGROUND PASS.");
				getDeathTalismanInCentreDoorCorrect = new NpcStep(this, NpcID.MOURNING_DEATHALTER_DWARF_VIS, new WorldPoint(1860, 4641, 0),
					"Get a Death Talisman by either getting the dwarf at the altar 50 items, or killing Dark Beasts.");
			}
			else
			{
				getDeathTalisman = new DetailedQuestStep(this, "You need to get a Death Talisman. Talk to the dwarf at the Death Altar to help him collect 50 items for one.");
				getDeathTalismanInCentre = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0),
					"Bring Thorgel the items he needs for a Death Talisman. TURN THE MIDDLE PILLAR TO POINT BACK " +
						"EAST OR YOU'LL HAVE TO RETURN VIA THE UNDERGROUND PASS.");
				getDeathTalismanInCentreDoorCorrect = new NpcStep(this, NpcID.MOURNING_DEATHALTER_DWARF_VIS, new WorldPoint(1860, 4641, 0),
					"Bring the dwarf the items he needs for a Death Talisman.");
			}
		}
		else
		{
			getDeathTalisman = new DetailedQuestStep(this, "You need to get a Death Talisman. Buy one, or talk to the dwarf at the Death Altar to help him collect 50 items for one.");
			getDeathTalismanInCentre = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0),
				"Get a Death Talisman and return. Buy one, or help Thorgel at the altar for one. TURN THE MIDDLE " +
					"PILLAR TO POINT BACK EAST OR YOU'LL HAVE TO RETURN VIA THE UNDERGROUND PASS.");
			getDeathTalismanInCentreDoorCorrect = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0),
				"Get a Death Talisman and return. Buy one, or help Thorgel at the altar for one.");
		}
		getDeathTalismanInCentre.addDialogStep("Okay sure thing, what do you need?");
		getDeathTalismanInCentreDoorCorrect.addDialogStep("Okay sure thing, what do you need?");
		getDeathTalismanInCentre.addDialogStep("I've got some of them with me.");
		getDeathTalismanInCentreDoorCorrect.addDialogStep("I've got some of them with me.");
		getDeathTalismanInCentre.addDialogStep("Yeah sure.");
		getDeathTalismanInCentreDoorCorrect.addDialogStep("Yeah sure.");


		enterMournerHQCharging = new ObjectStep(this, ObjectID.MOURNERSTEWDOOR, new WorldPoint(2551, 3320, 0),
			"Return to the Temple of Light with a Death Talisman, or items for Thorgel for his.", deathTalisman, newlyMadeCrystal, newKey, gasMask, mournerTop, mournerTrousers, mournerCloak, mournerGloves, mournerBoots);
		enterMournerHQCharging.addTeleport(westArdougneTeleport);
		enterMournerBasementCharging = new ObjectStep(this, ObjectID.MOURNING_HIDEOUT_TRAP_DOOR, new WorldPoint(2542, 3327, 0), "Enter the Mourner HQ basement.");
		enterMournerCaveCharging = new ObjectStep(this, ObjectID.MOURNER_HIDEOUT_DOOR4, new WorldPoint(2034, 4636, 0), "Enter the Mourner Caves.", newKey);
		enterUndergroundPass = new ObjectStep(this, ObjectID.REGICIDE_VOYAGE_TEMPLE_ENTRANCE, new WorldPoint(2314, 3217, 0),
			"You didn't unlock the light door from the Mourner entrance, so you'll need to return to the Death Altar via the Underground Pass. Enter from the Elven Lands entrance. Bring a Death Talisman, or items for Thorgel for his Death Talisman.",
			deathTalisman, newlyMadeCrystal);
		enterWell = new ObjectStep(this, ObjectID.REGICIDE_VOYAGE_TEMPLE_WELL2, new WorldPoint(2342, 9623, 0), "Enter the Well of Voyage.");
		leavePassCentre = new ObjectStep(this, ObjectID.UPASS_TEMPLEDOOR_CLOSED_RIGHT, new WorldPoint(2016, 4712, 1), "Leave the well area, and head to the dwarven camp below to the south.");
		enterSouthPass = new ObjectStep(this, ObjectID.CAVEWALLTUNNEL_UPASS_DOWN, new WorldPoint(2150, 4545, 1), "Enter the south cave to go to the lower level of the pass.");
		enterAltarFromBehind = new ObjectStep(this, ObjectID.CAVEWALLTUNNEL_TO_TEMPLE, new WorldPoint(2311, 9792, 0), "Enter the cave entrance behind the dwarf camp under Iban's area to the south.");
		enterDeathAltar = new ObjectStep(this, ObjectID.DEATHTEMPLE_RUINED, new WorldPoint(1860, 4639, 0),
			"Enter the death altar ruins.", deathTalisman.highlighted());
		enterDeathAltar.addIcon(ItemID.DEATH_TALISMAN);
		turnKeyMirrorCharging = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0), "Enter the central area, and turn the pillar's mirror west.");
		goUpToF1ForCharging = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Enter the Temple of Light, and go up the south staircase to the second floor.");
		goUpToF2ForCharging = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south stairs to the second floor.");
		goDownToMiddleFromSouthCharging = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down the staircase north of you.");
		goDownToCentreCharging = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1888, 4639, 1), "Go down to the central area.");

		useCrystalOnAltar = new ObjectStep(this, ObjectID.DEATH_ALTAR, new WorldPoint(2205, 4836, 0), "Use the newly made crystal on the death altar.", newlyMadeCrystalHighlight);
		useCrystalOnAltar.addIcon(ItemID.MOURNING_CRYSTAL_NEW_SAMPLE);

		leaveDeathAltar = new ObjectStep(this, ObjectID.DEATHTEMPLE_EXIT_PORTAL, new WorldPoint(2208, 4829, 0), "Leave the Death Altar and go use the charged crystal on the dark crystal.");
		turnPillarFromTemple = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_PILLAR_1_B, new WorldPoint(1881, 4639, 0), "Enter the central area, and turn the pillar's mirror east.", chargedCrystal);
		goUpFromCentre = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1888, 4639, 0), "Go up to the dark crystal.", chargedCrystal);
		goUpToNorthToCharge =  new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1891, 4642, 1), "Go up the stairs to the north.", chargedCrystal);
		useCrystalOnCrystal = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_OBSIDIAN_CRYSTAL, new WorldPoint(1909, 4639, 2), "Use the charged crystal on the dark crystal north of you.", chargedCrystalHighlight);
		useCrystalOnCrystal.addIcon(ItemID.MOURNING_CRYSTAL_NEW_POWERED);

		enterMournerHQCrystal = new ObjectStep(this, ObjectID.MOURNERSTEWDOOR, new WorldPoint(2551, 3320, 0),
			"Return to the Temple of Light with a Death Talisman, or items for Thorgel for his.", deathTalisman, newlyMadeCrystal, newKey, gasMask, mournerTop, mournerTrousers, mournerCloak, mournerGloves, mournerBoots);
		enterMournerHQCrystal.addTeleport(westArdougneTeleport);
		enterMournerBasementCrystal = new ObjectStep(this, ObjectID.MOURNING_HIDEOUT_TRAP_DOOR, new WorldPoint(2542, 3327, 0), "Enter the Mourner HQ basement.");
		enterMournerCaveCrystal = new ObjectStep(this, ObjectID.MOURNER_HIDEOUT_DOOR4, new WorldPoint(2034, 4636, 0), "Enter the Mourner Caves.", newKey);

		enterFloor1Crystal = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_BASE, new WorldPoint(1903, 4639, 0), "Enter the Temple of Light, and go up the south staircase to the second floor.");
		enterFloor2Crystal = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_STAIRS_BASE, new WorldPoint(1894, 4620, 1), "Go up the south stairs to the second floor.");
		goDownToMiddleFromSouthCrystal = new ObjectStep(this, ObjectID.MOURNING_TEMPLE_CIRCLE_STAIRS_TOP, new WorldPoint(1891, 4636, 2), "Go down the staircase north of you.");

		leaveDeathAltar.addSubSteps(enterMournerHQCrystal, enterMournerBasementCrystal, enterMournerCaveCrystal, enterFloor1Crystal, enterFloor2Crystal, goDownToMiddleFromSouthCrystal, useCrystalOnCrystal, goUpToNorthToCharge, goUpFromCentre, turnPillarFromTemple);

		returnToArianwyn = new NpcStep(this, NpcID.MOURNING_ARIANWYN_VIS, new WorldPoint(2354, 3170, 0), "Return to Arianwyn in Lletya.");
		returnToArianwyn.addTeleport(lletyaTeleport);
	}

	@Override
	public List<ItemRequirement> getItemRequirements()
	{
		return Arrays.asList(mournersOutfit, chisel, deathTalismanHeader, rope);
	}

	@Override
	public List<ItemRequirement> getItemRecommended()
	{
		return Arrays.asList(staminaPotions, prayerPotions, food, westArdougneTeleport, lletyaTeleport);
	}

	@Override
	public List<String> getCombatRequirements()
	{
		return Collections.singletonList("Able to survive Shadows (level 73) continually attacking you");
	}

	@Override
	public List<Requirement> getGeneralRequirements()
	{
		ArrayList<Requirement> req = new ArrayList<>();
		req.add(new QuestRequirement(QuestHelperQuest.MOURNINGS_END_PART_I, QuestState.FINISHED));
		return req;
	}

	@Override
	public QuestPointReward getQuestPointReward()
	{
		return new QuestPointReward(2);
	}

	@Override
	public List<ExperienceReward> getExperienceRewards()
	{
		return Collections.singletonList(new ExperienceReward(Skill.AGILITY, 60000));
	}

	@Override
	public List<ItemReward> getItemRewards()
	{
		return Arrays.asList(
				new ItemReward("A Crystal Trinket", ItemID.MOURNING_CRYSTAL_TRINKET, 1),
				new ItemReward("A Death Talisman", ItemID.DEATH_TALISMAN, 1));
	}

	@Override
	public List<UnlockReward> getUnlockRewards()
	{
		return Arrays.asList(
				new UnlockReward("Ability to craft Death Runes."),
				new UnlockReward("Ability to kill Dark Beasts and receive them as a slayer task."));
	}

	@Override
	public List<PanelDetails> getPanels()
	{
		List<PanelDetails> allSteps = new ArrayList<>();
		allSteps.add(new PanelDetails("Starting off",
			new ArrayList<>(Collections.singletonList(talkToArianwyn))));

		allSteps.add(new PanelDetails("Explore the caves",
			Arrays.asList(talkToEssyllt, enterCave, searchCorpse, goUpStairsTemple, goUpSouthLadder, goToMiddleFromSouth, goUpFromMiddleToNorth, useChisel, bringCrystalToArianwyn,
				talkToElunedAfterGivingCrystal, talkToArianwynAfterGivingCrystal), Arrays.asList(chisel, mournersOutfit),
			Arrays.asList(westArdougneTeleport, lletyaTeleport)));

		allSteps.add(new PanelDetails("Return to the Temple", Collections.singletonList(enterTempleOfLight),
			rope, mournersOutfit, deathTalisman, newKey, newlyIfOneTrip));

		allSteps.add(new PanelDetails("Puzzle 1", Arrays.asList(goUpStairsTempleC1, pullDispenser1, puzzle1Pillar1, puzzle1Pillar2, puzzle1Pillar3, puzzle1Pillar4, puzzle1Pillar5, climbWallSupport, searchBlueChest)));
		allSteps.add(new PanelDetails("Puzzle 2", Arrays.asList(pullDispenser2, puzzle2Pillar1, puzzle2Pillar2, puzzle2Pillar3, puzzle2Pillar4, puzzle2Pillar5, puzzle2Pillar6, searchMagentaChest)));
		allSteps.add(new PanelDetails("Puzzle 3", Arrays.asList(puzzle3Pillar6RemoveYellow, goUpLadderNorthForPuzzle3, puzzle3Pillar7, goDownFromF2NorthRoomPuzzle3, goUpToFloor2Puzzle3,
			puzzle3Pillar8, goDownFromF2Puzzle3, goDownFromF1Puzzle3, enterNorthWestRoomPuzzle3, puzzle3Pillar9, searchYellowChest)));

		allSteps.add(new PanelDetails("Puzzle 4", Arrays.asList(goUpToFirstFloorPuzzle4, puzzle4Pillar3RemoveCyan, goUpToFloor2Puzzle4, puzzle4Pillar8, puzzle4Pillar9, goDownFromF2Puzzle4, useRope, goDownRope, searchCyanChest), rope));

		PanelDetails placeBlueCrystalPanel = new PanelDetails("Puzzle 5 P1", Arrays.asList(climbUpRope, pullDispenser5, puzzle5Pillar1, puzzle5Pillar2, puzzle5Pillar3, puzzle5Pillar4, puzzle5Pillar5, climbWallSupportPuzzle5, puzzle5Pillar6));
		placeBlueCrystalPanel.setLockingStep(puzzle5PlaceBlue);
		allSteps.add(placeBlueCrystalPanel);
		allSteps.add(new PanelDetails("Puzzle 5 P2", Arrays.asList(puzzle5Pillar5RemoveMirror, puzzle5Pillar3RotateUp, goUpToFloor2Puzzle5, goDownToMiddleFromSouthPuzzle5,
			goUpFromMiddleToNorthPuzzle5, puzzle5Pillar7, goDownToMiddleFromNorthPuzzle5, puzzle5Pillar8, puzzle5Pillar9, puzzle5Pillar10, puzzle5Pillar11, goDownFromF2Puzzle5,
			goDownFromF1Puzzle5, puzzle5Pillar12, puzzle5Pillar13, puzzle5Pillar14, searchMagentaYellowChest)));

		allSteps.add(new PanelDetails("Reach the Death Altar", Arrays.asList(pullDispenser6, goUpToF1Puzzle6, puzzle6Pillar1,
			puzzle6Pillar2, goDownFromF1Puzzle6, puzzle6Pillar3, puzzle6Pillar4,
			puzzle6Pillar5, puzzle6Pillar6, puzzle6Pillar7, puzzle6Pillar8, goUpToF1Puzzle6, puzzle6Pillar9, goUpNorthLadderToF2Puzzle6, puzzle6Pillar10, goDownNorthLadderToF1Puzzle6, goUpToFloor2Puzzle6,
			puzzle6Pillar11, puzzle6Pillar12, puzzle6Pillar13, goDownToMiddleFromSouthPuzzle6, goUpFromMiddleToNorthPuzzle6, puzzle6Pillar14, puzzle6Pillar15, puzzle6Pillar16, puzzle6Pillar17, goDownToCentre, turnKeyMirror)));

		allSteps.add(new PanelDetails("Repair the defences", Arrays.asList(enterDeathAltarBarrier, getDeathTalisman, enterDeathAltar, useCrystalOnAltar, leaveDeathAltar, returnToArianwyn), deathTalisman, newlyMadeCrystal));

		return allSteps;
	}
}
