/*
 *
 *  * Copyright (c) 2021, Zoinkwiz <https://github.com/Zoinkwiz>
 *  * All rights reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions are met:
 *  *
 *  * 1. Redistributions of source code must retain the above copyright notice, this
 *  *    list of conditions and the following disclaimer.
 *  * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  *    this list of conditions and the following disclaimer in the documentation
 *  *    and/or other materials provided with the distribution.
 *  *
 *  * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.runelite.client.plugins.microbot.questhelper.requirements.var;

import net.runelite.client.plugins.microbot.questhelper.requirements.AbstractRequirement;
import net.runelite.client.plugins.microbot.questhelper.requirements.util.Operation;
import net.runelite.api.Client;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

public class VarplayerRequirement extends AbstractRequirement
{

	private final int varPlayerID;
	private final List<Integer> values;
	private final Operation operation;
	private final String displayText;

	private final int bitPosition;
	private final boolean bitIsSet;

	private final int bitShiftRight;

	public VarplayerRequirement(int varPlayerID, int value)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(value);
		this.operation = Operation.EQUAL;
		this.displayText = null;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, int value, String displayText)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(value);
		this.operation = Operation.EQUAL;
		this.displayText = displayText;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, int value, Operation operation)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(value);
		this.operation = operation;
		this.displayText = null;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, int value, Operation operation, String displayText)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(value);
		this.operation = operation;
		this.displayText = displayText;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, boolean bitIsSet, int bitPosition)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(-1);
		this.operation = Operation.EQUAL;
		this.displayText = null;

		this.bitPosition = bitPosition;
		this.bitIsSet = bitIsSet;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, boolean bitIsSet, int bitPosition, String displayText)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(-1);
		this.operation = Operation.EQUAL;
		this.displayText = displayText;

		this.bitPosition = bitPosition;
		this.bitIsSet = bitIsSet;
		this.bitShiftRight = -1;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, int value, int bitShiftRight)
	{
		this.varPlayerID = varPlayerID;
		this.values = List.of(value);
		this.operation = Operation.EQUAL;
		this.displayText = null;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = bitShiftRight;
		shouldCountForFilter = true;
	}

	public VarplayerRequirement(int varPlayerID, List<Integer> values, int bitShiftRight)
	{
		this.varPlayerID = varPlayerID;
		this.values = values;
		this.operation = Operation.EQUAL;
		this.displayText = null;

		this.bitPosition = -1;
		this.bitIsSet = false;
		this.bitShiftRight = bitShiftRight;
		shouldCountForFilter = true;
	}

	@Override
	public boolean check(Client client)
	{
		int varpValue = client.getVarpValue(varPlayerID);
		if (bitPosition >= 0)
		{
			return bitIsSet == BigInteger.valueOf(varpValue).testBit(bitPosition);
		}
		else if (bitShiftRight >= 0)
		{
			return values.stream().anyMatch(value -> operation.check(varpValue >> bitShiftRight, value));
		}
		return values.stream().anyMatch(value -> operation.check(varpValue, value));
	}

	@Nonnull
	@Override
	public String getDisplayText()
	{
		if (displayText != null)
		{
			return displayText;
		}
		if (bitPosition >= 0)
		{
			return varPlayerID + " must have the " + bitPosition + " bit set.";
		}
		return varPlayerID + " must be + " + operation.name().toLowerCase(Locale.ROOT) + " " + values;
	}
}
