package us.blockgame.lib.timer;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.timer.event.TimerTickEvent;

import java.text.DecimalFormat;

public class Timer {

    private final DecimalFormat decimalFormat = new DecimalFormat("00");

    private LibPlugin plugin;
    @Getter private String name;
    private int time;
    private BukkitTask task;
    private boolean countdown;
    private boolean before;
    private int period;

    public Timer(String name, int time, boolean countdown) {
        plugin = LibPlugin.getInstance();
        this.name = name;
        this.time = time;
        this.countdown = countdown;
        this.period = 20;
    }

    public Timer(String name, int time, boolean countdown, int period) {
        plugin = LibPlugin.getInstance();
        this.name = name;
        this.time = time;
        this.countdown = countdown;
        this.period = period;
    }

    public Timer(String name, int time, boolean countdown, boolean before) {
        plugin = LibPlugin.getInstance();
        this.name = name;
        this.time = time;
        this.countdown = countdown;
        this.before = before;
        this.period = 20;
    }

    public void start() {
        task = new BukkitRunnable() {
            public void run() {
                if (countdown) {
                    time--;
                    if (time == 0) {
                        stop();
                    }
                } else {
                    time++;
                }
                TimerTickEvent timerTickEvent = new TimerTickEvent(Timer.this);
                timerTickEvent.call();

            }
        }.runTaskTimer(this.plugin, before ? period : 0L, period);
    }

    public void stop() {
        task.cancel();
    }

    public String getTime() {
        int seconds = time % 60;
        int minutes = (time / 60) % 60;

        return decimalFormat.format(minutes) + ":" + decimalFormat.format(seconds);
    }

    public int getRawTime() {
        return time;
    }
}