package com.shmc.luckylootboxes.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class StylesUtil {

  private StylesUtil() {}

  public static MutableText getGoldTooltip(String id) {
    return new TranslatableText(id)
        .setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GOLD));
  }
}
