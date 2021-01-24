package com.tatsinktech.echat.model;

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
 * @create 2018-04-28 下午1:32
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "login_info")
public class LoginInfoDo extends AbstractModel<Long> {
    
    private Long userId;
    private String userName;
    private Integer status;
    private Date createTime;
    
    public LoginInfoDo(Long id, Long userId, String userName, Integer status, Date createTime) {
        super.setId(id);
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.createTime = createTime;
    }
    
    public static LoginInfoBuilder builder() {
        return new LoginInfoBuilder();
    }
    
    public static class LoginInfoBuilder {

        private Long id;
        private Long userId;
        private String userName;
        private Integer status;
        private Date createTime;
        
        public LoginInfoBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public LoginInfoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        
        public LoginInfoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }
        
        public LoginInfoBuilder status(Integer status) {
            this.status = status;
            return this;
        }
        
        public LoginInfoBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }
        
        public LoginInfoDo build() {
            return new LoginInfoDo(id, userId, userName, status, createTime);
        }
    }
    
}
