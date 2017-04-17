package top.linjia.wizarposapp.beans;

/**
 * ClassName: UserInfo
 * Description: 用户信息实体类,用于向数据库中存储
 * Created by 邻家新锐 on 2016/11/7 14:02
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class UserInfo {
    int userId;
    String username;
    String password;
    public UserInfo(int userId,String username,String password){
        this.userId=userId;
        this.username=username;
        this.password=password;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }
    public int getUserId(){
        return userId;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
}
