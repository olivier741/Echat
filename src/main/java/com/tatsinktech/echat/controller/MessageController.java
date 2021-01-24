package com.tatsinktech.echat.controller;


import com.tatsinktech.echat.enums.MessageTypeEnum;
import com.tatsinktech.echat.model.Message;
import com.tatsinktech.echat.model.MessageRecordDo;
import com.tatsinktech.echat.model.User;
import com.tatsinktech.echat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Send message controller
 */
@Controller
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SUBSCRIBE_MESSAGE_URI = "/topic/chat/message"; //Subscribe to receive message address

    private static final String IMAGE_PREFIX = "/upload/";  //The server stores the prefix of the upload image address

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserService userService;

    /**
     * Receive and forward messages
     * @param message
     */
    @MessageMapping("/chat/message")
    public void receiveMessage(Message message){
        message.setSendDate(new Date());
        message.setMessageType("text");
        logger.info(message.getSendDate() + "," + message.getUserName() + " send a message:" + message.getContent());
        //Save chat information
        User userByName = userService.getUserByName(message.getUserName());
        MessageRecordDo messageRecordDo = MessageRecordDo.messageRecordBuilder()
                .userId(userByName == null ? null : userByName.getId())
                .userName(message.getUserName()).content(message.getContent())
                .messageType(MessageTypeEnum.TEXT.getCode()).createTime(new Date()).build();
        userService.addUserMessageRecord(messageRecordDo);
        messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI, message);
    }

    /**
     * Receive and forward pictures
     * @param request
     * @param imageFile
     * @param userName
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public String handleUploadImage(HttpServletRequest request, @RequestParam("image")MultipartFile imageFile,
                                    @RequestParam("userName")String userName, Model model){
        if (!imageFile.isEmpty()){
            String imageName = userName + "_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(request.getSession().getServletContext().getRealPath(IMAGE_PREFIX) + "/" + imageName);
            if (!file.exists()){
                file.mkdirs();
            }
            try {
                //Upload picture to catalog
                imageFile.transferTo(file);

                Message message = new Message();
                message.setMessageType("image");
                message.setUserName(userName);
                message.setSendDate(new Date());
                // Picture src
                message.setContent(request.getContextPath() + IMAGE_PREFIX + imageName);

                //Save and send picture information
                User userByName = userService.getUserByName(message.getUserName());
                MessageRecordDo messageRecordDo = MessageRecordDo.messageRecordBuilder()
                        .userId(userByName == null ? null : userByName.getId())
                        .userName(userName).content(message.getContent())
                        .messageType(MessageTypeEnum.IMAGE.getCode()).createTime(new Date()).build();
                userService.addUserMessageRecord(messageRecordDo);

                messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI, message);
            } catch (IOException e) {
                logger.error("Image upload failed：" + e.getMessage(), e);
                return "upload false";
            }
        }
        return "upload success";
    }
}
