/*
 * Copyright (c) 2016-2017, Seth <Sethtroll3@gmail.com>
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * Copyright (c) 2019, Trevor <https://github.com/Trevor159>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.microbot.questhelper.overlays;

import net.runelite.client.plugins.microbot.questhelper.QuestHelperPlugin;
import net.runelite.client.plugins.microbot.questhelper.questhelpers.QuestHelper;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

public class QuestHelperOverlay extends OverlayPanel
{
	public static final Color TITLED_CONTENT_COLOR = new Color(190, 190, 190);

	private final QuestHelperPlugin plugin;

	@Inject
	public QuestHelperOverlay(QuestHelperPlugin plugin)
	{
		this.plugin = plugin;
		setLayer(OverlayLayer.UNDER_WIDGETS);
		setPriority(PRIORITY_HIGHEST);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.getConfig().showOverlay())
		{
			return super.render(graphics);
		}
		QuestHelper questHelper = plugin.getSelectedQuest();

		if (questHelper == null || questHelper.getCurrentStep() == null)
		{
			return null;
		}

		if (questHelper.getCurrentStep().getActiveStep().isShouldOverlayWidget() && getLayer() != OverlayLayer.ALWAYS_ON_TOP)
		{
			setLayer(OverlayLayer.ALWAYS_ON_TOP);
			plugin.getQuestOverlayManager().updateOverlay();
		}
		else if (getLayer() != OverlayLayer.UNDER_WIDGETS)
		{
			setLayer(OverlayLayer.UNDER_WIDGETS);
			plugin.getQuestOverlayManager().updateOverlay();
		}
		questHelper.getCurrentStep().makeOverlayHint(panelComponent, plugin, new ArrayList<>(), new ArrayList<>());

		return super.render(graphics);
	}
}
