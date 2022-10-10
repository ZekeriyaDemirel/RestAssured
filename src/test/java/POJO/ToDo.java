package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDo {
   private int userId;
   private int id;
   private String unvan;
   private Boolean completed;

   public Boolean getCompleted() {
      return completed;
   }

   public void setCompleted(Boolean completed) {
      this.completed = completed;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @JsonProperty("title")
   public String getUnvan() {
      return unvan;
   }

   public void setuUvan(String unvan) {
      this.unvan = unvan;
   }


   @Override
   public String toString() {
      return "ToDo{" +
              "userId=" + userId +
              ", id=" + id +
              ", unvan='" + unvan + '\'' +
              ", completed=" + completed +
              '}';
   }
}

 // "userId": 1,
 //         "id": 2,
 //         "title": "quis ut nam facilis et officia qui",
 //         "completed": false
