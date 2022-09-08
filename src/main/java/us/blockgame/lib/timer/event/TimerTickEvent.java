package us.blockgame.lib.timer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import us.blockgame.lib.event.BaseEvent;
import us.blockgame.lib.timer.Timer;

@AllArgsConstructor
public class TimerTickEvent extends BaseEvent {

    @Getter private Timer timer;

}
