/*
 * Copyright (c) 2019-2021 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.connector.network.translators.java.title;

import com.github.steveice10.mc.protocol.packet.ingame.server.title.ServerSetTitleTextPacket;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import org.geysermc.connector.network.session.GeyserSession;
import org.geysermc.connector.network.translators.PacketTranslator;
import org.geysermc.connector.network.translators.Translator;
import org.geysermc.connector.network.translators.chat.MessageTranslator;

@Translator(packet = ServerSetTitleTextPacket.class)
public class JavaSetTitleTextTranslator extends PacketTranslator<ServerSetTitleTextPacket> {

    @Override
    public void translate(GeyserSession session, ServerSetTitleTextPacket packet) {
        String text;
        if (packet.getText() == null) { // This can happen, see https://github.com/KyoriPowered/adventure/issues/447
            text = " ";
        } else {
            text = MessageTranslator.convertMessage(packet.getText(), session.getLocale());
        }

        SetTitlePacket titlePacket = new SetTitlePacket();
        titlePacket.setType(SetTitlePacket.Type.TITLE);
        titlePacket.setText(text);
        titlePacket.setXuid("");
        titlePacket.setPlatformOnlineId("");
        session.sendUpstreamPacket(titlePacket);
    }
}
