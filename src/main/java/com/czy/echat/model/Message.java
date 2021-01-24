package com.czy.echat.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户发送消息的实体
 */
@Entity
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "message")
public class Message extends AbstractModel<Long> {

    private String userName; //Sender
    private Date sendDate; //Send date
    private String content; //Send content
    private String messageType;//Send message type ("text" text, "image" picture)

}
