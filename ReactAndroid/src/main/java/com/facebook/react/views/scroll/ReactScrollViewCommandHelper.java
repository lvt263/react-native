/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * <p>This source code is licensed under the MIT license found in the LICENSE file in the root
 * directory of this source tree.
 */
package com.facebook.react.views.scroll;

import androidx.annotation.Nullable;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;

/**
 * Helper for view managers to handle commands like 'scrollTo'. Shared by {@link
 * ReactScrollViewManager} and {@link ReactHorizontalScrollViewManager}.
 */
public class ReactScrollViewCommandHelper {

  public static final int COMMAND_SCROLL_TO = 1;
  public static final int COMMAND_SCROLL_TO_END = 2;
  public static final int COMMAND_FLASH_SCROLL_INDICATORS = 3;

   /**
   * Prior to users being able to specify a duration when calling "scrollTo",
   * they could specify an "animate" boolean, which would use Android's
   * "smoothScrollTo" method, which defaulted to a 250 millisecond
   * animation:
   * https://developer.android.com/reference/android/widget/Scroller.html#startScroll
   */
  public static final int LEGACY_ANIMATION_DURATION = 250;


  public interface ScrollCommandHandler<T> {
    void scrollTo(T scrollView, ScrollToCommandData data);

    void scrollToEnd(T scrollView, ScrollToEndCommandData data);

    void flashScrollIndicators(T scrollView);
  }

  public static class ScrollToCommandData {

    public final int mDestX, mDestY, mDuration;

    ScrollToCommandData(int destX, int destY, int duration) {
      mDestX = destX;
      mDestY = destY;
      mDuration = duration;
    }
  }

  public static class ScrollToEndCommandData {

    public final int mDuration;

    ScrollToEndCommandData(boolean animated, int duration) {
      mDuration = duration;
      mAnimated = animated;
    }
  }

  public static Map<String, Integer> getCommandsMap() {
    return MapBuilder.of(
        "scrollTo",
        COMMAND_SCROLL_TO,
        "scrollToEnd",
        COMMAND_SCROLL_TO_END,
        "flashScrollIndicators",
        COMMAND_FLASH_SCROLL_INDICATORS);
  }

  public static <T> void receiveCommand(
      ScrollCommandHandler<T> viewManager,
      T scrollView,
      int commandType,
      @Nullable ReadableArray args) {
    Assertions.assertNotNull(viewManager);
    Assertions.assertNotNull(scrollView);
    Assertions.assertNotNull(args);
    switch (commandType) {
      case COMMAND_SCROLL_TO:
        {
          scrollTo(viewManager, scrollView, args);
          return;
        }
      case COMMAND_SCROLL_TO_END:
        {
          scrollToEnd(viewManager, scrollView, args);
          return;
        }
      case COMMAND_FLASH_SCROLL_INDICATORS:
        viewManager.flashScrollIndicators(scrollView);
        return;

      default:
        throw new IllegalArgumentException(
            String.format(
                "Unsupported command %d received by %s.",
                commandType, viewManager.getClass().getSimpleName()));
    }
  }

  public static <T> void receiveCommand(
      ScrollCommandHandler<T> viewManager,
      T scrollView,
      String commandType,
      @Nullable ReadableArray args) {
    Assertions.assertNotNull(viewManager);
    Assertions.assertNotNull(scrollView);
    Assertions.assertNotNull(args);
    switch (commandType) {
      case "scrollTo":
        {
          scrollTo(viewManager, scrollView, args);
          return;
        }
      case "scrollToEnd":
        {
          scrollToEnd(viewManager, scrollView, args);
          return;
        }
      case "flashScrollIndicators":
        viewManager.flashScrollIndicators(scrollView);
        return;

      default:
        throw new IllegalArgumentException(
            String.format(
                "Unsupported command %s received by %s.",
                commandType, viewManager.getClass().getSimpleName()));
    }
  }

  private static <T> void scrollTo(
      ScrollCommandHandler<T> viewManager, T scrollView, @Nullable ReadableArray args) {
    int destX = Math.round(PixelUtil.toPixelFromDIP(args.getDouble(0)));
    int destY = Math.round(PixelUtil.toPixelFromDIP(args.getDouble(1)));
    boolean animated = args.getBoolean(2);
    Log.d("aaaaa", "aaaaa")
    int duration = (int) Math.round(args.getDouble(3));
        viewManager.scrollTo(scrollView, new ScrollToCommandData(destX, destY, animated, duration));
  }

  private static <T> void scrollToEnd(
    Log.d("aaaaa", "aaaaa")
      ScrollCommandHandler<T> viewManager, T scrollView, @Nullable ReadableArray args) {
    boolean animated = args.getBoolean(0);
   int duration = (int) Math.round(args.getDouble(1));
        viewManager.scrollToEnd(scrollView, new ScrollToEndCommandData(animated, duration));
  }
}
