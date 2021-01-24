package com.tatsinktech.echat.websocket;

import com.tatsinktech.echat.enums.LoginTypeEnum;
import com.tatsinktech.echat.model.LoginInfoDo;
import com.tatsinktech.echat.model.ParticipantRepository;
import com.tatsinktech.echat.model.User;
import com.tatsinktech.echat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.Map;

/**
 * websocket断开连接处理，监听SessionDisconnectEvent事件，当有人下线就通知其他用户
 */
@Component
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserService userService;

    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    /**
     * When the sessionDisconnectEvent is published, this method will be called
     * to remove the websocket sessionAttributes from the message in the event
     * Remove the left user from it, delete the user from the online user map,
     * and notify other users
     *
     * @param sessionDisconnectEvent
     */
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        Map<String, User> activeSessions = participantRepository.getActiveSessions();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        User disconnectSession = (User) sessionAttributes.get("user");
        String disconnectUserName = disconnectSession.getName();
        if (participantRepository.containsUserName(disconnectUserName)) {
            User removeUser = participantRepository.remove(disconnectUserName);
            removeUser.setLogoutDate(new Date());
            //Save logout information
            User userByName = userService.getUserByName(removeUser.getName());
            LoginInfoDo loginInfo = LoginInfoDo.builder().userId(userByName == null ? null : userByName.getId())
                    .userName(removeUser.getName()).
                    status(LoginTypeEnum.LOGOUT.getCode()).createTime(new Date()).build();
            userService.addUserLoginInfo(loginInfo);
            logger.info(removeUser.getLogoutDate() + ", " + removeUser.getName() + " logout.");
            messagingTemplate.convertAndSend(SUBSCRIBE_LOGOUT_URI, removeUser);
        }
    }
}
