package us.blockgame.lib.redis.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import us.blockgame.lib.event.BaseEvent;

import java.util.Map;

@AllArgsConstructor
public class RedisReceieveEvent extends BaseEvent {

    @Getter private String channel;
    @Getter private Map<String, Object> messageMap;

}
