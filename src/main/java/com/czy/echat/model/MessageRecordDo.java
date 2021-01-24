package com.czy.echat.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author MccreeFei
 * @create 2018-04-28 下午1:35
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "message_record")
public class MessageRecordDo extends AbstractModel<Long> {
    
    private Long userId;
    private String userName;
    private Integer messageType;
    private String content;
    private Date createTime;

  public MessageRecordDo(Long id,Long userId, String userName, Integer messageType, String content, Date createTime) {
        super.setId(id);
        this.userId = userId;
        this.userName = userName;
        this.messageType = messageType;
        this.content = content;
        this.createTime = createTime;
    }


    public static MessageRecordBuilder messageRecordBuilder(){
        return new MessageRecordBuilder();
    }

    public static class MessageRecordBuilder{
        private Long id;
        private Long userId;
        private String userName;
        private Integer messageType;
        private String content;
        private Date createTime;

        public MessageRecordBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MessageRecordBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public MessageRecordBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public MessageRecordBuilder messageType(Integer messageType) {
            this.messageType = messageType;
            return this;
        }

        public MessageRecordBuilder content(String content) {
            this.content = content;
            return this;
        }

        public MessageRecordBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public MessageRecordDo build(){
            return new MessageRecordDo(id, userId, userName, messageType, content, createTime);
        }
    }
}
