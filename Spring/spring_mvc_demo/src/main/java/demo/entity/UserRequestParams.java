package demo.entity;

public class UserRequestParams {
  private String userName;
  private String password;

  @Override
  public String toString() {
    return "UserRequestParams{" +
            "userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
