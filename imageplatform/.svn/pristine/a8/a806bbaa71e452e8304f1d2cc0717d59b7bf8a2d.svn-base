package com.cloudhua.imageplatform.service.event;

/**
 * Created by yangchao on 2017/8/16.
 */
public abstract class Event {

   private boolean isContinue = true;

   private String breakMessage;

   private String breakStatus;

   public final String getBreakMessage() {
       return breakMessage;
   }

   public final String getBreakStatus() {
       return breakStatus;
   }

   public final void breakEvent(String breakStatus, String breakMessage) {
       isContinue = false;
       this.breakMessage = breakMessage;
       this.breakStatus = breakStatus;
   }

   public final boolean isContinue() {
       return isContinue;
   }

}
