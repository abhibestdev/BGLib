package us.blockgame.lib.redis;

import us.blockgame.lib.redis.event.RedisReceieveEvent;

import java.util.Map;

public class RedisListener {

    @RedisSubscriber("bglib")
    public void onBGLib(Map<String, Object> messageMap) {
        //Call RedisReceiveEvent
        new RedisReceieveEvent("bglib", messageMap).call();
    }
}
