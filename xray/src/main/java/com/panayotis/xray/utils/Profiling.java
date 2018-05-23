/* (c) 2017 by CrossMobile.tech
 *
 * CrossMobile is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
 *
 * CrossMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CrossMobile; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.panayotis.xray.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Profiling {

    private static int frames = -1;
    private static Consumer<Integer> feedback;
    private static Timer timer;

    public static synchronized void tick() {
        frames++;
    }

    public static synchronized void start() {
        if (timer == null) {
            timer = new Timer("FPS timer", true);
            frames = 0;
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    synchronized (Profiling.class) {
                        if (feedback != null)
                            feedback.accept(frames);
                    }
                    frames = 0;
                }
            }, 0, 1000);
        }
    }

    public static synchronized void stop() {
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    public static void setFeedback(Consumer<Integer> result) {
        Profiling.feedback = result;
    }

}
